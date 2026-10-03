package angel_zero.inventario.ordenesProductos;

import java.math.BigDecimal;
import java.util.List;

import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;

public record DTOMostrarOrden(
			Long idProducto,
			String nombreProducto,
			int cantidad,
			BigDecimal precio
		) {

	public DTOMostrarOrden (EntidadOrdenesProductos productosEnElCarrito) {
		
		this(
			productosEnElCarrito.getProductoSolicitado().getId(),
			productosEnElCarrito.getProductoSolicitado().getNombreProducto(),
			productosEnElCarrito.getCantidad(),
			productosEnElCarrito.getPrecio()
		);
		
	}
	
}
