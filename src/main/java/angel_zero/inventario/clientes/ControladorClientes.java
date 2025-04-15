package angel_zero.inventario.clientes;

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
import angel_zero.inventario.direcciones.DTOInfoDireccion;
import angel_zero.inventario.direcciones.DTORegistrarDireccion;
import angel_zero.inventario.direcciones.ImpServDirecciones;
import angel_zero.inventario.historialOrdenes.DTOCompraRealizada;
import angel_zero.inventario.historialOrdenes.DTOConfirmacionVaciarCarrito;
import angel_zero.inventario.historialOrdenes.DTOMontoTotalCarrito;
import angel_zero.inventario.ordenesProductos.DTOMostrarOrden;
import angel_zero.inventario.pagos.DTORegistarMetodoPago;
import angel_zero.inventario.rolesPermisos.ImpServRolesUsuarios;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ControladorClientes {

	private final ImplementacionServiciosClientes servicioClientes;
	private final ServicioAccesoUsuarios servicioAcceso;
	private final ImpServDirecciones servDirecciones;
	private final ImpServRolesUsuarios servRolUsu;
	
	
	@PostMapping("/registrar")
	public ResponseEntity registrarNuevoCliente (@RequestBody @Valid DTORegistroNuevoCliente nuevoCliente) {
		
		return servRolUsu.registrarUsuario(nuevoCliente);
		
	}
	
	@PostMapping("/accesar")
	public ResponseEntity accesoCliente(@RequestBody @Valid DTOCredencialesAcceso credenciales) {
		//System.out.println(credenciales.contrasena() + " " + credenciales.correo());
		return servicioAcceso.accesoUsuario(credenciales);
		
	}
	
	@GetMapping("/carrito-de-compras")
	public ResponseEntity<Page<DTOMostrarOrden>> comprasEnCarrito(Pageable paginacion) {
		
		return servicioClientes.comprasEnCarrito(paginacion);
		
	}
	
	@PostMapping("/eliminar-todos-los-productos-del-carrito")
	public ResponseEntity elimnarProductosCarrito(@RequestBody DTOConfirmacionVaciarCarrito confirmacion) {
		
		return servicioClientes.eliminarOrdenCompleta(confirmacion);
		
	}
	
	@DeleteMapping("/carrito-de-compras/{idProducto}")
	public ResponseEntity eliminarPrductoDeCarrito (@PathVariable Long idProducto) {
		
		return servicioClientes.eliminarProductoDeOrden(idProducto);
		
	}
	
	@GetMapping("/carrito-de-compras/monto")
	public ResponseEntity<DTOMontoTotalCarrito> montoTotalCarrito() {
		
		return servicioClientes.montoDelCarrito();
		
	}
	
	@GetMapping("/lista-de-compras-realizadas")
	public ResponseEntity<Page<DTOCompraRealizada>> comprasRealizadas(Pageable paginacion) {
		
		return servicioClientes.comprasRealizadas(paginacion);
		
	}
	
	@GetMapping("/lista-de-compras-realizadas/{idOrden}")
	public ResponseEntity<Page<DTOMostrarOrden>> productosEnOrdenTerminada(@PathVariable Long idOrden, Pageable paginacion) {
		
		return servicioClientes.productosEnOrdenTerminada(paginacion, idOrden);
		
	}
	
	@PutMapping("/editar-informacion")
	public ResponseEntity editarInformacionCliente (@RequestBody @Valid DTOEditarCliente edicionCliente) {
		
		return servicioClientes.editarInformacionCliente(edicionCliente);
		
	}

	@PostMapping("/registrar-metodo-pago")
	public ResponseEntity registrarMetodoPago(@RequestBody @Valid DTORegistarMetodoPago registroMetodoPago) {
		
		return servicioClientes.registrarMetodoPago(registroMetodoPago);
		
	}
	
	@PostMapping("/registro-domicilio")
	public ResponseEntity registrarDireccion(@RequestBody DTORegistrarDireccion registroDireccion) {
		
		
		return servDirecciones.anadirDireccionCliente(registroDireccion);
		
	}
	 
	@GetMapping("/domicilios-registrados")
	public ResponseEntity <Page<DTOInfoDireccion>> listarDirecciones(Pageable paginacion) {
		
		return servDirecciones.listarDireccionesClientes(paginacion);
		
	}
	
	@Transactional
	@PutMapping("/domicilios-registrados/{idDireccion}")
	public ResponseEntity editarDireccionClientes (@PathVariable Long idDireccion, 
			@RequestBody DTOEdicionDireccion edicionDireccion) {
		
		return servDirecciones.editarDireccionCliente(idDireccion, edicionDireccion);
		
	}
	
	@Transactional
	@DeleteMapping("/domicilios-registrados/{idDireccion}")
	public ResponseEntity eliminarDireccionClientes (@PathVariable Long idDireccion) {
		
		return servDirecciones.eliminarDireccionCliente(idDireccion);
		
	}
	
	@GetMapping("/realizar-compra")
	public ResponseEntity realizarCompra() {
		
		return servicioClientes.verificarRequisitos();
		
	}
	
	@Transactional
	@PostMapping("/finalizar-compra")
	public ResponseEntity finalizarCompra( ) {
		
		return servicioClientes.realizarCompraSeria();
		
	}
	
}
