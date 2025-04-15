package angel_zero.inventario.direcciones;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.proveedores.EntidadProveedores;

public interface RepositorioDirecciones extends JpaRepository <EntidadDirecciones, Long> {
	
	Page <EntidadDirecciones> findByIdProveedor(EntidadProveedores proveedor, Pageable paginacion);
	
	Page <EntidadDirecciones> findByIdCliente(EntidadClientes cliente, Pageable paginacion);
	
	Optional <EntidadDirecciones> findByIdAndIdCliente(Long id, EntidadClientes idCliente);
	
	Optional <EntidadDirecciones> findByIdAndIdProveedor(Long id, EntidadProveedores idProveedor);
	
	boolean existsByIdCliente(EntidadClientes idCliente);
	
	@Query("""
		SELECT d FROM EntidadDirecciones d 
		WHERE d.idCliente = :idCliente 
		AND d.direccionPreferida = true
			""")
	
	Optional <EntidadDirecciones> encontrarDireccionPreferidaCliente(EntidadClientes idCliente);

}
