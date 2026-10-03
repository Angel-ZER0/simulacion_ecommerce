package angel_zero.inventario.proveedores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import angel_zero.inventario.marcas.DTOMarcaAnadida;
import angel_zero.inventario.productos.DTOActualizarProducto;
import angel_zero.inventario.productos.DTOCrearProducto;
import angel_zero.inventario.productos.DTOInformacionProducto;

public interface IntServProveedores {

	ResponseEntity registrarProovedor(DTORegistrarProveedor registroProovedor);
	
	ResponseEntity <Page<DTOInformacionProducto>> listarPorMarcaNombreProductoCategoria(FiltroLista lista, Pageable paginacion);
	
	ResponseEntity <Page<DTOInformacionProducto>> productosAgotados(FiltroLista lista, Pageable paginacion);
	
	/*
	ResponseEntity anadirMarca (DTOMarcaAnadida marcaNueva, EntidadProovedores proovedor);
	
	ResponseEntity anadirProducto(DTOCrearProducto nuevoProducto, EntidadProovedores proovedor);
	
	ResponseEntity modificarProducto(DTOActualizarProducto actualizacionProducto);
	
	ResponseEntity productorPorProovedor(EntidadProovedores proovedor, Pageable paginacion);
	*/
}
