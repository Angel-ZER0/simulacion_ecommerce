package angel_zero.inventario.rolesPermisos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRolesUsuarios extends JpaRepository <EntidadRelacionRolesUsuarios, Long>{
	
	boolean existsByCorreo(String correo);
	
	Optional <EntidadRelacionRolesUsuarios> findByCorreo (String correo);
	 
	//Optional <EntidadRelacionRolesUsuarios> findByUsername(String username);
	
	@Query("select u from EntidadRelacionRolesUsuarios u join fetch u.rolesUsuario where u.correo = :username")
	EntidadRelacionRolesUsuarios buscarUsuario(String username);
	
	@Query("""
			SELECT r FROM EntidadRelacionRolesUsuarios e 
	        JOIN e.rolesUsuario r 
	        WHERE e.id = :idUsuario
			""")
	List<EntidadRoles> permisosUsuario(@Param("idUsuario") Long idUsuario);
	
}