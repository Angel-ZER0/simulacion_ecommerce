package angel_zero.inventario.categoria;

import java.util.Optional;

public record DTOInformacionCategoria(Long id, String cotegoria) {

	public DTOInformacionCategoria(EntidadCategoria categoria) {
		this(categoria.getId(), categoria.getCategoria());
	}

	public DTOInformacionCategoria(Optional<EntidadCategoria> categoria) {
		this(categoria.get().getId(), categoria.get().getCategoria());
	}
	
}
