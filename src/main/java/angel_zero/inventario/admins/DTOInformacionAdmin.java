package angel_zero.inventario.admins;

public record DTOInformacionAdmin(
			String nombreUsuario
		) {

	public DTOInformacionAdmin (EntidadAdmins admin) {
		
		this(admin.getNombreUsuario());
		
	}
	
}
