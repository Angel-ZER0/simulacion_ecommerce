package angel_zero.inventario.direcciones;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioEstados extends JpaRepository <EntidadEstados, Long> {

	@Query("""
			SELECT e FROM EntidadEstados e
			WHERE e.nombreEstado LIKE %:nombreEstado%
			ORDER BY LENGTH (e.nombreEstado) ASC
			LIMIT 1
			""")
	Optional <EntidadEstados> encontrarEstadoPorCadena(String nombreEstado);
	
}
