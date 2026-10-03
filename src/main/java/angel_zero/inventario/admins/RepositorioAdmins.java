package angel_zero.inventario.admins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAdmins extends JpaRepository <EntidadAdmins, Long >{

	boolean existsByNombreUsuario(String nombreUsuario);
	
}
