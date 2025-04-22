package angel_zero.inventario.historialOrdenes;

import java.math.BigDecimal;

public record DTOCompraRealizada(Long id, String fecha, BigDecimal montoTotal, String estadoPago) {

	public DTOCompraRealizada(EntidadHistorialOrdenes ordenAnterior) {
		
		this(
			ordenAnterior.getId(), 
			ordenAnterior.getFechaFiniquitpOrden().toString().replace("T", " Hora: ").substring(0, 22), 
			ordenAnterior.getTotalAPagar(),
			ordenAnterior.getEstadoTransaccion().getEstado()
		);
		
	}
	
}
