package angel_zero.inventario.pagos;

public record DTOInfoTarjeta(String numerosTarjeta, String fechaVencimiento, String tipoTarjeta) {
	
	public DTOInfoTarjeta(EntidadTarjetaPago tarjeta) {
		
		this("**** **** **** " + tarjeta.getNumerosTarjeta().substring(12, 16), tarjeta.getFechaExpiracion().toString(), tarjeta.getTipoTarjeta().getTipoTarjeta());
		
	}

}
