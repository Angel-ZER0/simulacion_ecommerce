package angel_zero.inventario.ordenesProductos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.proveedores.EntidadProveedores;
import jakarta.transaction.Transactional;

@Repository
public interface RepositorioOrdenesProductos extends JpaRepository <EntidadOrdenesProductos, Long>, 
	JpaSpecificationExecutor <EntidadOrdenesProductos> {

	@Query("""
		    SELECT op FROM EntidadOrdenesProductos op
		    WHERE op.idOrdenHistorial.ordenCliente = :cliente
		    AND op.idOrdenHistorial.activa = true
		    """)
	Page <EntidadOrdenesProductos> listaDeProductosEnOrden (@Param("cliente") EntidadClientes cliente, Pageable Paginacion);
	
	@Query("""
		    SELECT op FROM EntidadOrdenesProductos op
		    WHERE op.idOrdenHistorial.ordenCliente = :cliente
		    AND op.idOrdenHistorial.id = :idOrden 
		    AND op.idOrdenHistorial.activa = false
		    """)
	Page <EntidadOrdenesProductos> listaDeProductosEnOrdenTerminada (@Param("cliente") EntidadClientes cliente, 
			@Param("idOrden") Long idOrden, Pageable Paginacion);
	/*
	@Query("""
		    SELECT op FROM EntidadOrdenesProductos op
		    WHERE op.idOrdenHistorial.ordenCliente = :cliente
		    AND op.idOrdenHistorial.activa = :estadoOrden
		    """)
	Page <EntidadOrdenesProductos> listaDeProductosEnLaOrden (@Param("cliente") EntidadClientes cliente, 
			@Param("estadoOrden") boolean estadoOrden, Pageable Paginacion);
	*/
	
	/*
	@Query("""
		    SELECT op FROM EntidadOrdenesProductos op
		    WHERE op.idOrdenHistorial.id = :idHistorialOrden
		    AND op.productoSolicitado.id = :idProducto
	""")
	boolean verificarProductoAnadido (@Param("idHistorialOrden") Long idHistorialOrden,
			@Param("idProducto") Long idProducto);
	*/
	EntidadOrdenesProductos findByIdOrdenHistorialAndProductoSolicitado(EntidadHistorialOrdenes idOrden,
			EntidadProductos productoSolicitado);
	
	@Query("""
		    SELECT op FROM EntidadOrdenesProductos op
		    WHERE op.idOrdenHistorial = :ordenHistorial
		    """)
	List <EntidadOrdenesProductos> productosEnCarrito (EntidadHistorialOrdenes ordenHistorial);

	@Query(
		"""
		SELECT op from EntidadOrdenesProductos op
		WHERE op.idOrdenHistorial.ordenCliente = :cliente
		AND op.idOrdenHistorial.activa = true
		AND op.productoSolicitado.id = :idProducto	
		"""
	)
	Optional <EntidadOrdenesProductos> productoAEliminar (@Param("idProducto") Long idProducto, @Param("cliente") EntidadClientes clientes);
	
    boolean existsByProductoSolicitadoIdAndIdOrdenHistorialOrdenClienteAndIdOrdenHistorialActiva(
            @Param("idProducto") Long idProducto,
            @Param("cliente") EntidadClientes cliente,
            @Param("activa") boolean activa
     );
	
	@Transactional
	@Modifying
	@Query(
		"""
		DELETE from EntidadOrdenesProductos op
		WHERE op.idOrdenHistorial.ordenCliente = :cliente
		AND op.idOrdenHistorial.activa = true
		AND op.productoSolicitado.id = :idProducto	
		"""
	)
	void eliminarProductoDeOrdenActiva(@Param("idProducto") Long idProducto, @Param("cliente") EntidadClientes clientes);
	
	@Transactional
	@Modifying
	@Query("""
			DELETE FROM EntidadOrdenesProductos op
			WHERE op.idOrdenHistorial = :idOrden
			AND op.idOrdenHistorial.activa = true
			AND op.idOrdenHistorial.ordenCliente = :cliente
			""")
	void eliminarOrdenCompleta(@Param("idOrden") EntidadHistorialOrdenes idOrden, @Param("cliente") EntidadClientes cliente);
	
}
