package angel_zero.inventario.productos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IntServProductos {

	public ResponseEntity crearNuevoProducto(DTOCrearProducto nuevoProducto);
	
	public ResponseEntity <Page<DTOInformacionProducto>> listarProductosEnVenta(Pageable paginacion);
	
	public ResponseEntity <Page<DTOInformacionProducto>> productosDeProovedor(Pageable paginación);
	
	public ResponseEntity agregarAListaDeCompras(Long idProducto, DTODetalleCompra detalleCompra);
	
	public ResponseEntity <Page<DTOInformacionProducto>> busquedaAvanzada(FiltroBusquedaProductos filtro,
			Pageable paginacion);
	
	public ResponseEntity listarProductosAgotados(Pageable paginacion);
	
	public ResponseEntity actualizarProducto (Long idProducto, DTOActualizarProducto actualizacionProducto);
	
	public ResponseEntity eliminarProducto (Long id);
	
}
