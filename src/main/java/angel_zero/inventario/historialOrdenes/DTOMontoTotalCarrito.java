package angel_zero.inventario.historialOrdenes;

import java.math.BigDecimal;

public record DTOMontoTotalCarrito(BigDecimal montoDelCarrito) {

	public DTOMontoTotalCarrito(EntidadHistorialOrdenes encontrarOrden) {
		
		this(encontrarOrden.getTotalAPagar());
		
	}
	
}
