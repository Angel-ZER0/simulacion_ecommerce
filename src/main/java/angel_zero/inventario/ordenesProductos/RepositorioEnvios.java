package angel_zero.inventario.ordenesProductos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import angel_zero.inventario.direcciones.EntidadDirecciones;

public interface RepositorioEnvios extends JpaRepository <EntidadEnvios, Long>{
	/*
	@Query("""
			SELECT e from EntidadEnvios WHERE e.id ?=
			""")
	Page<EntidadDirecciones> direccionesDesdeDondeLlegaraPaquete(Long idEnvio, Pageable paginacion);
	*/
}
