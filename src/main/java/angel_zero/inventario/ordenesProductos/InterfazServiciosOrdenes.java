package angel_zero.inventario.ordenesProductos;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import angel_zero.inventario.historialOrdenes.DTOCrearHostorialOrden;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;

public interface InterfazServiciosOrdenes {

	//public ResponseEntity crearOrden(DTOCrearOrden ordenProducto);
	
	//public ResponseEntity listarOrden(EntidadHistorialOrdenes ordenAMostrar, Pageable paginacion);
	
	public ResponseEntity elimarProductoDeOrden(EntidadOrdenesProductos ordenActiva, Long idProducto);
	
}
