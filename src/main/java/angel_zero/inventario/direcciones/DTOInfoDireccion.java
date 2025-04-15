package angel_zero.inventario.direcciones;

public record DTOInfoDireccion(
		Long id,
		String calle,
		String colonia,
		String codigoPostal,
		String municipio,
		String estado,
		String referenciasOpcionales
		) {

	public DTOInfoDireccion(EntidadDirecciones direccion) {
		
		this(
			direccion.getId(),
			direccion.getCalle(),
			direccion.getColonia(),
			direccion.getCodigoPostal(),
			direccion.getMunicipio().getNombre(),
			direccion.getEstado().getNombreEstado(),
			direccion.getReferenciasOpcionales()
		);
		
	}
	
}
