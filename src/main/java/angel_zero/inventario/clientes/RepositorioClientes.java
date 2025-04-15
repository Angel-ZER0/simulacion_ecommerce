package angel_zero.inventario.clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioClientes extends JpaRepository <EntidadClientes, Long> {

	//public boolean existsByUUID(String UUID);
	
	boolean existsByTelefono(String telefono);
	
}
