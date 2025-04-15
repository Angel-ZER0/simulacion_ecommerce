package angel_zero.inventario.direcciones;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioMunicipiosAlcaldias extends JpaRepository <EntidadMunicipiosAlcaldias, Long> {

	@Query("""
			SELECT m FROM EntidadMunicipiosAlcaldias m
			WHERE m.nombre LIKE %:nombreDemarcacion%
			ORDER BY LENGTH (m.nombre) ASC
			LIMIT 1
			""")
	Optional <EntidadMunicipiosAlcaldias> encontrarMunicipio (String nombreDemarcacion);
	
}