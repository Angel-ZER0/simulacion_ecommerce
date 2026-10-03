package angel_zero.inventario.productos;

import jakarta.validation.constraints.NotNull;

public record DTOActualizarProducto(
			String nombreProducto,
			double precio,
			int cantidadDisponible,
			String marca,
			String categoria
		) {

}
