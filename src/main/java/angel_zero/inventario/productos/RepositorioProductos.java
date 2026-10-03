package angel_zero.inventario.productos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import angel_zero.inventario.marcas.EntidadMarcas;
import angel_zero.inventario.proveedores.EntidadProveedores;

@Repository
public interface RepositorioProductos extends JpaRepository <EntidadProductos, Long>,
		JpaSpecificationExecutor<EntidadProductos>{

	@Query("select p from EntidadProductos p where p.cantidadDisponible > 0")
	public Page <EntidadProductos> productosEnExistencia(Pageable Paginacion);
	
	@Query("select p from EntidadProductos p where p.cantidadDisponible = 0")
	public Page <EntidadProductos> productosAgotados(Pageable paginacion);
	
	@Query("select p from EntidadProductos p where p.marca.proovedorMarca = :proovedor")
	public Page <EntidadProductos> productosPorProovedor(@Param("proovedor") EntidadProveedores proovedor, Pageable paginacion);
	
	@Query("DELETE from EntidadProductos p where p.marca = :marca")
	public void eliminarProductosPorMarca(EntidadMarcas marca);
	
	@Query("SELECT p from EntidadProductos p WHERE p.marca.marca = :nombreMarca")
	public Page <EntidadProductos> buscarProductosPorMarca(@Param("nombreMarca") String nombreMarca, Pageable paginacion);
	
	@Query("SELECT p from EntidadProductos p WHERE p.marca.proovedorMarca = :proovedor AND p.id = :idProducto")
	public Optional <EntidadProductos> actualizarInfoProducto(@Param("proovedor") EntidadProveedores proovedor, 
			@Param("idProducto") Long idProducto)
;}
