package angel_zero.inventario.rolesPermisos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRoles extends JpaRepository <EntidadRoles, Long> {

	EntidadRoles findByNombreRol(String nombreRol);
	
}
