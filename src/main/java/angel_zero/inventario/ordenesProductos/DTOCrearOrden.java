package angel_zero.inventario.ordenesProductos;

import java.math.BigDecimal;
import java.util.List;

import angel_zero.inventario.historialOrdenes.DTOCrearHostorialOrden;
import angel_zero.inventario.productos.EntidadProductos;

public record DTOCrearOrden(
			DTOCrearHostorialOrden nuevaOrden,
			Long idProducto,
			int cantidad,
			BigDecimal precio
		) {

}
