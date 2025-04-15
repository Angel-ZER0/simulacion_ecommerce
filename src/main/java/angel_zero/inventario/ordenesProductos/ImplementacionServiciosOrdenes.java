package angel_zero.inventario.ordenesProductos;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.clientes.RepositorioClientes;
import angel_zero.inventario.historialOrdenes.DTOCrearHostorialOrden;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.historialOrdenes.RepositorioHistorialOrdenes;
import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.productos.RepositorioProductos;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplementacionServiciosOrdenes implements InterfazServiciosOrdenes{
	
	private final RepositorioProductos repoProductos;
	private final RepositorioClientes repoClientes;
	private final RepositorioOrdenesProductos repoOrdenes;
	private final RepositorioHistorialOrdenes repoHistorial;
	
	/*
	@Override
	public ResponseEntity crearOrden(DTOCrearOrden ordenProducto) {
		
		EntidadClientes cliente = repoClientes.getReferenceById(ordenProducto.nuevaOrden().id());
		EntidadProductos anadirProducto = repoProductos.getReferenceById(ordenProducto.idProducto());
		Optional<EntidadHistorialOrdenes> ordenActiva = repoHistorial.localizarOrdenActiva(cliente.getId());
		
		if (ordenActiva.isPresent()) {
			
			EntidadOrdenesProductos anadirOrden = new EntidadOrdenesProductos(ordenProducto, anadirProducto);
			ordenActiva.get().getNumeroDeOrden().add(anadirOrden);
			return ResponseEntity.ok(new DTOMostrarOrden(ordenActiva.get().getNumeroDeOrden()));
			
		} else {
			
			EntidadOrdenesProductos anadirOrden = repoOrdenes.save(new EntidadOrdenesProductos(ordenProducto, anadirProducto));
			EntidadHistorialOrdenes nuevaOrden = new EntidadHistorialOrdenes(cliente, anadirOrden);
			return ResponseEntity.ok(new DTOMostrarOrden(nuevaOrden.getNumeroDeOrden()));
		}
			
	}
	*/
	/*
	@Override
	public ResponseEntity listarOrden(EntidadHistorialOrdenes ordenAMostrar, Pageable paginacion) {
		
		
		return ResponseEntity.ok(repoOrdenes.listaDeProductosEnOrden(ordenAMostrar.getId(), paginacion));
		
	}
	*/
	@Override
	public ResponseEntity elimarProductoDeOrden(EntidadOrdenesProductos ordenActuca, Long idProducto) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
