package angel_zero.inventario.marcas;

public record DTOInformacionMarca(Long id, String marca, String proovedor) {

	public DTOInformacionMarca(EntidadMarcas marca) {
		this(marca.getId(), marca.getMarca(), marca.getProovedorMarca().getNombreEmpresa());
	}
	
}
