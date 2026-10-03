package angel_zero.inventario.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import angel_zero.inventario.historialOrdenes.DTOCompraRealizada;
import angel_zero.inventario.historialOrdenes.DTOConfirmacionVaciarCarrito;
import angel_zero.inventario.historialOrdenes.DTOEstadoABuscar;
import angel_zero.inventario.historialOrdenes.DTOMontoTotalCarrito;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.ordenesProductos.DTOMostrarOrden;
import angel_zero.inventario.pagos.DTORegistarMetodoPago;

public interface InterfazServiciosClientes {

	public ResponseEntity registrarNuevoCliente(DTORegistroNuevoCliente nuevoCliente);

	//public ResponseEntity accesoCliente(DTOCredenciales credencialesClientes);

	public ResponseEntity listarClientes(Pageable Paginacion);
	
	public ResponseEntity <Page<DTOMostrarOrden>> comprasEnCarrito(Pageable paginacion);
	
	public ResponseEntity <DTOMontoTotalCarrito> montoDelCarrito();
	
	public ResponseEntity <String> eliminarProductoDeOrden(Long idProducto);
	
	public ResponseEntity eliminarOrdenCompleta(DTOConfirmacionVaciarCarrito confirmacion);
	
	public ResponseEntity <Page<DTOCompraRealizada>> comprasRealizadas(Pageable paginacion, DTOEstadoABuscar estado);
	
	public ResponseEntity registrarMetodoPago(DTORegistarMetodoPago registrarMetodoPago);
	
	public ResponseEntity editarInformacionCliente(DTOEditarCliente cliente);
	
	public ResponseEntity eliminarCliente(Long id);
	
	public ResponseEntity realizarCompra(Long idDireccion);
	
	public ResponseEntity cancelarOrden(Long idHistorialOrdenes);
	
	public ResponseEntity verificarRequisitos();
	
	public ResponseEntity realizarCompraSeria();
	
}
