package angel_zero.inventario.pagos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import angel_zero.inventario.clientes.EntidadClientes;

public interface RepositorioMetodosPago extends JpaRepository <EntidadMetodosPago, Long> {

	boolean existsByIdCliente(EntidadClientes cliente);
	
	@Query("""
			SELECT mp from EntidadMetodosPago mp
			WHERE mp.idCliente = :idCliente
			AND mp.metodoPreferido = true
			""")
	Optional <EntidadMetodosPago> encontrarMetodoPreferidoCliente(EntidadClientes idCliente);
	
}
