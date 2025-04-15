package angel_zero.inventario.proveedores;

public record DTOInformacionEmpresa(Long id, String nombreEmpresa, String telefono) {

	public DTOInformacionEmpresa(EntidadProveedores proovedor) {
		
		this(
			proovedor.getId(),
			proovedor.getNombreEmpresa(),
			proovedor.getTelefono()
			);
		
	}
	
}
