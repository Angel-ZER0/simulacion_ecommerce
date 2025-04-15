package angel_zero.inventario.proveedores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import angel_zero.inventario.credencialesAcceso.DTOCredencialesAcceso;
import angel_zero.inventario.credencialesAcceso.ServicioAccesoUsuarios;
import angel_zero.inventario.direcciones.DTOEdicionDireccion;
import angel_zero.inventario.direcciones.DTORegistrarDireccion;
import angel_zero.inventario.direcciones.ImpServDirecciones;
import angel_zero.inventario.marcas.ImpServMarcas;
import angel_zero.inventario.productos.DTOActualizarProducto;
import angel_zero.inventario.productos.DTOCrearProducto;
import angel_zero.inventario.productos.DTOInformacionProducto;
import angel_zero.inventario.productos.ImpServProductos;
import angel_zero.inventario.rolesPermisos.ImpServRolesUsuarios;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/proveedor")
@RequiredArgsConstructor
public class ControladorProveedores {

	private final ImpServProveedores servProveedores;
	private final ImpServMarcas servMarcas;
	private final ImpServProductos servProductos;
	private final ServicioAccesoUsuarios servicioAcceso;
	private final ImpServRolesUsuarios servRolUsu;
	private final ImpServDirecciones servDirecciones;
	
	@PostMapping("/registro-empresa")
	ResponseEntity registrarProovedor (@RequestBody @Valid DTORegistrarProveedor registroProovedor) {
		
		return servRolUsu.registrarUsuario(registroProovedor);
		
	}
	
	@PostMapping("/acceso-proveedor")
	ResponseEntity accesoUsuarios (@RequestBody @Valid DTOCredencialesAcceso credencialesAcceso) {
		
		return servicioAcceso.accesoUsuario(credencialesAcceso);
		
	}
	
	@PostMapping("/anadir-producto")
	public ResponseEntity exhibirNuevoProducto(@RequestBody @Valid DTOCrearProducto productoNuevo) {
		
		return servProductos.crearNuevoProducto(productoNuevo);
		
	}

	@GetMapping("/todas-las-marcas")
	public ResponseEntity listarMarcas(Pageable paginacion) {
		
		return servMarcas.listarMarcas(paginacion);
		
	}
	
	@PostMapping("/listar-por-filtro")
	public ResponseEntity <Page<DTOInformacionProducto>> buscarMarcas(@RequestBody FiltroLista filtro, Pageable paginacion) {
		
		return servProveedores.listarPorMarcaNombreProductoCategoria(filtro, paginacion);
		
	}
	
	@PostMapping("/productos-agotados")
	public ResponseEntity <Page<DTOInformacionProducto>> listarProductosAgotados(@RequestBody FiltroLista filtro, Pageable paginacion) {
		
		return servProveedores.productosAgotados(filtro, paginacion);
		
	}
	
	@PutMapping("/actualizar-producto/{idProducto}")
	@Transactional
	public ResponseEntity actualizarProducto (@RequestBody DTOActualizarProducto actualizacionProducto, @PathVariable Long idProducto) {
		
		return servProductos.actualizarProducto(idProducto, actualizacionProducto);
		
	}
	
	@DeleteMapping("/{idMarca}")
	@Transactional
	public ResponseEntity eliminarMarca(@PathVariable Long idMarca) {
		
		return servMarcas.eliminarMarca(idMarca);
		
	}
	
	@DeleteMapping("/{idProducto}")
	@Transactional
	public ResponseEntity eliminarProducto (@PathVariable Long idProducto) {
		
		return servProductos.eliminarProducto(idProducto);
		
	}
	
	@PostMapping("/registrar-domicilio")
	public ResponseEntity registrarDomicilioProveedor(@RequestBody DTORegistrarDireccion registroDireccion) {
		
		return servDirecciones.anadirDireccionProveedor(registroDireccion);
		
	}
	
	@GetMapping("/domicilios-registrados")
	public ResponseEntity listarDomiciliosProveedor(Pageable paginacion) {
		
		return servDirecciones.listarDireccionesProveedores(paginacion);
		
	}
	
	@Transactional
	@PutMapping("/domicilios-registrados/{idDireccion}")
	public ResponseEntity editarDireccionProveedor(@PathVariable Long idDireccion, @RequestBody DTOEdicionDireccion edicionDireccion) {
		
		return servDirecciones.editarDireccionProveedor(idDireccion, edicionDireccion);
		
	}
	
	@Transactional
	@DeleteMapping("/domicilios-registrados/{idDireccion}")
	public ResponseEntity eliminarDireccionProveedor(@PathVariable Long idDireccion) {
		
		return servDirecciones.eliminarDireccionProovedor(idDireccion);
		
	}
	
}
