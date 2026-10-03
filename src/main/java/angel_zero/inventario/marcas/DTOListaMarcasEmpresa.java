package angel_zero.inventario.marcas;

public record DTOListaMarcasEmpresa(Long idMarca, String marca) {

	public DTOListaMarcasEmpresa(EntidadMarcas marca) {
		
		this(marca.getId(), marca.getMarca());
		
	}
	
}
