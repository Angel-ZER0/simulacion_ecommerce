package angel_zero.inventario.pagos;

public record DTOInformacionPayPal(String propietarioCuenta, String email) {
	
	public DTOInformacionPayPal(EntidadCuentaPayPal cuentaPayPal) {
		
		this(cuentaPayPal.getPropietarioMetodo(), cuentaPayPal.getEmailCuentaPayPal());
		
	}

}
