package angel_zero.inventario.ordenesProductos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import angel_zero.inventario.direcciones.EntidadDirecciones;

public interface RepositorioEnvios extends JpaRepository <EntidadEnvios, Long>, JpaSpecificationExecutor <EntidadEnvios>{
	
	@Query("""
			SELECT e from EntidadEnvios e 
			WHERE e.id = :idEnvio
			AND e.estadoPaquete.id = 1
		""")
	Optional <EntidadEnvios> encontrarEnvioPorIdEnvio(@Param("idEnvio") Long idEnvio);
	
	@Query("""
			SELECT e from EntidadEnvios e 
			WHERE e.id = :idEnvio
			AND e.estadoPaquete.estado = :estadoPaquete
			""")
	Optional <EntidadEnvios> localizarEnvio(@Param("idEnvio") Long idEnvio, 
			@Param("estadoPaquete") String estadoPaquete);
	
	@Query("""
			SELECT e from EntidadEnvios e
			WHERE e.direccionCliente.idCliente.rolesCliente = :correo
			""")
	List <EntidadEnvios> encontraPorEmailCliente(@Param("correo") String correo);
	
}
