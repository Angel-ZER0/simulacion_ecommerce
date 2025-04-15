package angel_zero.inventario.productos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTOCrearProducto (
			@NotBlank String nombreProducto,
			@NotBlank @Pattern(regexp = "\\d+(\\.\\d+)?") String precio,
			@NotBlank @Pattern(regexp = "\\d+") String cantidadDisponible,
			@NotBlank @Pattern(regexp = "\\d+") String idMarca,
			@NotBlank String categoria
		) {
	
}
