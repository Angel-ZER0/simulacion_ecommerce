package angel_zero.inventario.productos;

public record DTOInformacionProducto(
			Long id,
			String nombreProducto,
			double precio,
			int cantidadDisponible,
			String marca,
			String categoria
		) {

	public DTOInformacionProducto (EntidadProductos producto) {
		this(
			producto.getId(), 
			producto.getNombreProducto(), 
			producto.getPrecio(), 
			producto.getCantidadDisponible(), 
			producto.getMarca().getMarca(), 
			producto.getCategoria().getCategoria()
		);
	}
	
}
