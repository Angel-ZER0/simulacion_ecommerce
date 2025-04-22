package angel_zero.inventario.historialOrdenes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.proveedores.EntidadProveedores;

@Repository
public interface RepositorioHistorialOrdenes extends JpaRepository <EntidadHistorialOrdenes, Long> {
	
	/*
	@Query("""
			select ho from EntidadHistorialOrdenes ho
			join EntidadClientes c where c.id = :idClientes and ho.activa = true; 
				""")
	boolean buscarOrdenEnCola (@Param("clienteId") Long clienteId);
	*/
	@Query("""
			select ho from EntidadHistorialOrdenes ho 
			where ho.ordenCliente = :cliente 
			AND ho.activa = true
	""")
	public Optional<EntidadHistorialOrdenes> localizarOrdenActiva (@Param("cliente") EntidadClientes cliente);
	
	@Query("""
			select ho from EntidadHistorialOrdenes ho 
			where ho.ordenCliente = :cliente 
			AND ho.activa = false
			AND ho.estadoTransaccion = :estado
	""")
	Page <EntidadHistorialOrdenes> productosEnOrdenesAnteriores(EntidadClientes cliente, EntidadEstadoPagoOrden estado, 
			Pageable paginacion);
	
}