package angel_zero.inventario.marcas;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import angel_zero.inventario.proveedores.EntidadProveedores;
import jakarta.transaction.Transactional;

public interface RepositorioMarcas extends JpaRepository <EntidadMarcas, Long> {

	Optional <EntidadMarcas> findByMarca(String marca);
	
	Boolean existsByMarca(String marca);
	
	@Query("""
			SELECT m from EntidadMarcas m WHERE m.proovedorMarca = :proovedor
			""")
	Page <EntidadMarcas> marcasPorEmpresa(@Param("proovedor") EntidadProveedores proovedor, Pageable paginacion);
	
	@Modifying
	@Transactional
	@Query ("""
			DELETE FROM EntidadMarcas m 
			WHERE m.id = :idMarca 
			AND m.proovedorMarca = :proovedorMarca
			""")
	void eliminarMarca(@Param("idMarca") Long idMarca, 
			@Param("proovedorMarca") EntidadProveedores proovedorMarca);
	
	@Query("""
			SELECT m FROM EntidadMarcas m 
			WHERE m.proovedorMarca = :proovedorMarca 
			AND m.id = :idMarca
			""")
	
	Optional<EntidadMarcas> seleccionarMarcaPorProovedor(@Param("idMarca") Long idMarca, 
			@Param("proovedorMarca") EntidadProveedores proovedorMarca);
	
}
