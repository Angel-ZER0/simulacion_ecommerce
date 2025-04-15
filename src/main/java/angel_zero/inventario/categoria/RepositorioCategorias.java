package angel_zero.inventario.categoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCategorias extends JpaRepository <EntidadCategoria, Long>{
	
	Optional <EntidadCategoria> findByCategoria(String categoria);

}
