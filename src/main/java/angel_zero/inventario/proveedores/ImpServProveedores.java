package angel_zero.inventario.proveedores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import angel_zero.inventario.categoria.EntidadCategoria;
import angel_zero.inventario.categoria.RepositorioCategorias;
import angel_zero.inventario.configSeguridad.ServicioEncriptarContrasena;
import angel_zero.inventario.marcas.DTOInformacionMarca;
import angel_zero.inventario.marcas.DTOMarcaAnadida;
import angel_zero.inventario.marcas.EntidadMarcas;
import angel_zero.inventario.marcas.RepositorioMarcas;
import angel_zero.inventario.productos.DTOActualizarProducto;
import angel_zero.inventario.productos.DTOCrearProducto;
import angel_zero.inventario.productos.DTOInformacionProducto;
import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.productos.EspecificacionProducto;
import angel_zero.inventario.productos.RepositorioProductos;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import angel_zero.inventario.rolesPermisos.ServSelecUsuario;
import angel_zero.inventario.tokenGen.ServicioToken;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServProveedores implements IntServProveedores {
	
	private final RepositorioProveedores repoProovedores;
	private final RepositorioRolesUsuarios repoRolUsu;
	private final RepositorioRoles repoRoles;
	private final RepositorioMarcas repoMarcas;
	private final RepositorioCategorias repoCategorias;
	private final RepositorioProductos repoProductos;
	private final ServSelecUsuario servSelecUsuario;
	private final ServicioEncriptarContrasena encriptarContrasena;

	@Override
	public ResponseEntity registrarProovedor(DTORegistrarProveedor registroProovedor) {
		
		if (repoProovedores.existsByNombreEmpresa(registroProovedor.nombreEmpresa())) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una empresa con ese nombre registrada.");
			
		} 
		
		List <EntidadRoles> roles = new ArrayList <EntidadRoles>();
		
		EntidadRoles rolProovedor = repoRoles.findByNombreRol("proovedor");
		
		roles.add(rolProovedor);
		
		EntidadProveedores nuevoProovedor = repoProovedores.save(new EntidadProveedores(registroProovedor));
		
		EntidadRelacionRolesUsuarios registroNuevoProovedor = 
				repoRolUsu.save(new EntidadRelacionRolesUsuarios(nuevoProovedor, roles,
						registroProovedor.credenciales().correo(),
						encriptarContrasena.contrasenaEncriptada(registroProovedor.credenciales().contrasena())));
		
		return ResponseEntity.ok(new DTOInformacionEmpresa(nuevoProovedor));
		
	}

	@Override
	public ResponseEntity <Page<DTOInformacionProducto>> listarPorMarcaNombreProductoCategoria(FiltroLista lista, Pageable paginacion) {
		
		EntidadProveedores proovedor = (EntidadProveedores) servSelecUsuario.obtenerEntidadRelacionada();
		/*
		Specification<EntidadProductos> spec = EspecificacionEmpresa.criteriosDeFiltrado(
			lista.nombreProducto(), 
			lista.marca(), 
			lista.categoria())
		.and((root, query, cb) -> cb.like(root.get("marca").get("proovedorMarca").get("nombreEmpresa"), proovedor.getNombreEmpresa()));
		
		return ResponseEntity.ok(repoProductos.findAll(spec, paginacion).map(DTOInformacionProducto::new));
		*/
		
		Specification<EntidadProductos> spec = EspecificacionProducto.criteriosDeFiltrado(
				lista.nombreProducto(),
				lista.marca(),
				proovedor.getNombreEmpresa(),
				lista.categoria()
		);
		
		return ResponseEntity.ok(repoProductos.findAll(spec, paginacion).map(DTOInformacionProducto::new));
	}

	@Override
	public ResponseEntity <Page<DTOInformacionProducto>> productosAgotados(FiltroLista lista, Pageable paginacion) {
		
		EntidadProveedores proovedor = (EntidadProveedores) servSelecUsuario.obtenerEntidadRelacionada();
		Specification<EntidadProductos> spec = EspecificacionProducto.criteriosDeFiltrado(
				lista.nombreProducto(),
				lista.marca(),
				proovedor.getNombreEmpresa(),
				lista.categoria()
		).and((root, query, cb) -> cb.equal(root.get("cantidadDisponible"), "0"));
		
		return ResponseEntity.ok(repoProductos.findAll(spec, paginacion).map(DTOInformacionProducto::new));
		
	}
	
	

	/*
	@Override
	public ResponseEntity anadirMarca(DTOMarcaAnadida marcaNueva, EntidadProovedores proovedor) {
		
		if (repoMarcas.existsByMarca(marcaNueva.marca())) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una marca registrada con ese nombre");
			
		}
		
		EntidadMarcas nuevaMarca = repoMarcas.save(new EntidadMarcas(marcaNueva, proovedor));
		
		return ResponseEntity.ok(new DTOInformacionMarca(nuevaMarca));
		
	}

	@Override
	public ResponseEntity anadirProducto(DTOCrearProducto nuevoProducto, EntidadProovedores proovedor) {
		
		EntidadMarcas marca = repoMarcas.findByMarca(nuevoProducto.marca());
		
		if (!proovedor.getMarcaEmpresa().contains(marca)) {
			
			throw new IllegalStateException("Operación no permitida");
			
		} else {
		
		EntidadCategoria categoria = repoCategorias.findByCategoria(nuevoProducto.categoria());
		EntidadProductos producto = repoProductos.save(new EntidadProductos(nuevoProducto, marca, categoria));
		return ResponseEntity.ok(new DTOMostrarInformacion(producto));
		
		}
		
	}

	@Override
	public ResponseEntity modificarProducto(DTOActualizarProducto actualizacionProducto) {

		EntidadProductos actualizarProducto = repoProductos.getReferenceById(actualizacionProducto.id());

		if (actualizacionProducto.nombreProducto() != null) {

			actualizarProducto.actualizarNombreProducto(actualizacionProducto);

		}

		if (actualizacionProducto.precio() > 0) {

			actualizarProducto.actualizarPrecio(actualizacionProducto);

		}

		if (actualizacionProducto.cantidadDisponible() >= 0) {

			actualizarProducto.actualizarCantidad(actualizacionProducto);

		}

		if (actualizacionProducto.marca() != null) {

			EntidadMarcas marca = repoMarcas.findByMarca(actualizacionProducto.marca());

			if (marca != null) {

				actualizarProducto.actualizarMarca(marca);

			}

		}

		if (actualizacionProducto.categoria() != null) {

			EntidadCategoria categoria = repoCategorias.findByCategoria(actualizacionProducto.categoria());

			if (categoria != null) {

				actualizarProducto.actualizarCategoria(categoria);

			}

		}

		return ResponseEntity.ok(new DTOMostrarInformacion(actualizarProducto));

	}

	@Override
	public ResponseEntity productorPorProovedor(EntidadProovedores proovedor, Pageable paginacion) {
		
		return ResponseEntity.ok(repoProductos.productosPorProovedor(proovedor, paginacion)
				.map(DTOMostrarInformacion::new));
		
	}
	*/
}
