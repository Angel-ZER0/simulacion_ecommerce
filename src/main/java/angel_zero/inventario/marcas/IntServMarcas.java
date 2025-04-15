package angel_zero.inventario.marcas;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IntServMarcas {

	public ResponseEntity anadirMarca(DTOMarcaAnadida nuevaMarca);
	
	public ResponseEntity actualizarMarca(Long idMarca, DTOMarcaAnadida actualizarMarca);
	
	public ResponseEntity listarMarcas(Pageable paginacion);
	
	public ResponseEntity eliminarMarca(Long idMarca);
	
}
