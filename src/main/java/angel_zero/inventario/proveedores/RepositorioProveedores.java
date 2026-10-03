package angel_zero.inventario.proveedores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;

public interface RepositorioProveedores extends JpaRepository <EntidadProveedores, Long> {

	boolean existsByNombreEmpresa (String nombreEmpresa);
	
	@Query("""
		    SELECT DISTINCT p.marca.proovedorMarca FROM EntidadHistorialOrdenes h
		    JOIN h.numeroDeOrden o
		    JOIN o.productoSolicitado p
		    JOIN p.marca m
		    JOIN m.proovedorMarca prov
		    WHERE h = :ordenHistorial
		    """)
	List<EntidadProveedores> encontrarProveedoresPorOrden(@Param("ordenHistorial") EntidadHistorialOrdenes ordenHistorial);

	
}
