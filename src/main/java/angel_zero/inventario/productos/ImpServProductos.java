package angel_zero.inventario.productos;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import angel_zero.inventario.categoria.EntidadCategoria;
import angel_zero.inventario.categoria.RepositorioCategorias;
import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionCategoria404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionExistenciasDeProducto;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionMarca404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionProducto404;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.historialOrdenes.RepositorioHistorialOrdenes;
import angel_zero.inventario.marcas.EntidadMarcas;
import angel_zero.inventario.marcas.RepositorioMarcas;
import angel_zero.inventario.ordenesProductos.DTOMostrarOrden;
import angel_zero.inventario.ordenesProductos.EntidadOrdenesProductos;
import angel_zero.inventario.ordenesProductos.RepositorioOrdenesProductos;
import angel_zero.inventario.proveedores.EntidadProveedores;
import angel_zero.inventario.rolesPermisos.ServSelecUsuario;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServProductos implements IntServProductos{
	
	private final RepositorioProductos repoProductos;
	private final RepositorioMarcas repoMarcas;
	private final RepositorioCategorias repoCategorias;
	private final RepositorioHistorialOrdenes repoHistorial;
	private final RepositorioOrdenesProductos repoOrdenes;
	private final ServSelecUsuario servUsuario;
	private final UriComponentsBuilder uriComponentsBuilder;
	
	BigDecimal calcularMontoProducto(double valorProducto, int cantidadProducto) {
		
		return BigDecimal.valueOf(valorProducto * cantidadProducto);
		
	}

	void calcularMontoTotal(EntidadHistorialOrdenes historialOrdenes) {
		
		List <EntidadOrdenesProductos> ordenesCarrito= repoOrdenes.productosEnCarrito(historialOrdenes);
		BigDecimal montoTotal = ordenesCarrito.stream().map(op -> op.getPrecio()).reduce(BigDecimal.ZERO, BigDecimal::add);
		historialOrdenes.setTotalAPagar(montoTotal);
		
	}
	
	@Override
	public ResponseEntity crearNuevoProducto(DTOCrearProducto nuevoProducto) {
		
		EntidadMarcas marca = repoMarcas.seleccionarMarcaPorProovedor(Long.valueOf(nuevoProducto.idMarca()), 
				(EntidadProveedores) servUsuario.obtenerEntidadRelacionada()).orElseThrow(ExcepcionMarca404::new);
		EntidadCategoria categoria = repoCategorias.findByCategoria(nuevoProducto.categoria()).orElseThrow(ExcepcionCategoria404::new);
		EntidadProductos producto = repoProductos.save(new EntidadProductos(nuevoProducto, marca, categoria));
		URI url = uriComponentsBuilder.path("/productos/{idProducto}").buildAndExpand(producto.getId()).toUri();
		DTOInformacionProducto dtoNuevoProducto = new DTOInformacionProducto(producto);
		return ResponseEntity.created(url).body(dtoNuevoProducto);
		
	}
	
	@Override
	public ResponseEntity <Page<DTOInformacionProducto>> listarProductosEnVenta(Pageable paginacion) {
		
		return ResponseEntity.ok(repoProductos.productosEnExistencia(paginacion).map(DTOInformacionProducto::new));
	}
	
	@Override
	public ResponseEntity <Page<DTOInformacionProducto>> productosDeProovedor(Pageable paginación) {
		
		
		return ResponseEntity.ok(repoProductos.productosPorProovedor((EntidadProveedores) servUsuario.obtenerEntidadRelacionada(), paginación)
				.map(DTOInformacionProducto::new));
		
	};
	
	@Override
	public ResponseEntity agregarAListaDeCompras(Long idProducto, DTODetalleCompra detalleCompra) {
		
		EntidadClientes cliente = (EntidadClientes) servUsuario.obtenerEntidadRelacionada();

		EntidadProductos producto = repoProductos.findById(idProducto)
				.orElseThrow(ExcepcionProducto404::new);
				
		if (detalleCompra.cantidad() > producto.getCantidadDisponible()) {
			
			throw new ExcepcionExistenciasDeProducto("tu pedido excede la cantidad existente del producto");
			
		}
		
		//EntidadProductos productoConseguido = producto.get();
		
		EntidadHistorialOrdenes ordenActual = repoHistorial.localizarOrdenActiva(cliente)
				.orElseGet(() -> repoHistorial.save(new EntidadHistorialOrdenes(cliente)));
		Optional <EntidadOrdenesProductos> modificarOrden = Optional.ofNullable(repoOrdenes.findByIdOrdenHistorialAndProductoSolicitado(ordenActual, producto));
		
		if (modificarOrden.isPresent()) {
			
			modificarOrden.get().setCantidad(modificarOrden.get().getCantidad() + detalleCompra.cantidad());
			modificarOrden.get().setPrecio(BigDecimal.valueOf(producto.getPrecio() * modificarOrden.get().getCantidad()));
			calcularMontoTotal(ordenActual);
			return ResponseEntity.ok(new DTOMostrarOrden(modificarOrden.get()));
			
		}
		
		EntidadOrdenesProductos anadirProductoAOrden = repoOrdenes.save(new EntidadOrdenesProductos(producto, ordenActual, 
				detalleCompra, calcularMontoProducto(producto.getPrecio(), detalleCompra.cantidad())));
		calcularMontoTotal(ordenActual);
		
		
		return ResponseEntity.ok(new DTOMostrarOrden(anadirProductoAOrden));
		
	}

	public ResponseEntity <Page<DTOInformacionProducto>> busquedaAvanzada(FiltroBusquedaProductos filtro, Pageable paginacion) {
		
		Specification<EntidadProductos> spec = EspecificacionProducto.criteriosDeFiltrado(
				filtro.nombre(),
				filtro.marca(),
				filtro.empresa(),
				filtro.categoria()
		).and((root, query, cb) -> cb.greaterThan(root.get("cantidadDisponible"), 0));
		
		System.out.println("Valores en el servicio:");
		System.out.println(spec.getClass().toString());
	
		return ResponseEntity.ok(repoProductos.findAll(spec, paginacion).map(DTOInformacionProducto::new));
		
	}
	
	
	
	@Override
	public ResponseEntity listarProductosAgotados(Pageable paginacion) {
		
		return ResponseEntity.ok(repoProductos.productosAgotados(paginacion).map(DTOInformacionProducto::new));
		
	}

	@Override
	public ResponseEntity actualizarProducto(Long idProducto, DTOActualizarProducto actualizacionProducto) {
		
		EntidadProveedores proovedor = (EntidadProveedores) servUsuario.obtenerEntidadRelacionada();
		
		EntidadProductos actualizarProducto =repoProductos.actualizarInfoProducto(proovedor, idProducto)
				.orElseThrow(ExcepcionProducto404::new);
		
		if (actualizacionProducto.nombreProducto() != null) {
			
			actualizarProducto.actualizarNombreProducto(actualizacionProducto);
			
		}
		
		if (actualizacionProducto.precio() > 0) {
			
			actualizarProducto.actualizarPrecio(actualizacionProducto);
			
		}
		
		if (actualizacionProducto.cantidadDisponible() > 0) {
			
			actualizarProducto.actualizarCantidadPorProveedor(actualizacionProducto);
			
		}
		
		if (actualizacionProducto.marca() != null) {
			
			EntidadMarcas marca = repoMarcas.findByMarca(actualizacionProducto.marca())
					.orElseThrow(ExcepcionMarca404::new);
			actualizarProducto.actualizarMarca(marca);

		}
		
		if (actualizacionProducto.categoria() != null) {
			
			EntidadCategoria categoria = repoCategorias.findByCategoria(actualizacionProducto
					.categoria()).orElseThrow(ExcepcionCategoria404::new);
			actualizarProducto.actualizarCategoria(categoria);
		
		}
		
		return ResponseEntity.ok(new DTOInformacionProducto(actualizarProducto));
		
	}

	@Override
	public ResponseEntity eliminarProducto(Long id) {
		
		repoProductos.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}

}
