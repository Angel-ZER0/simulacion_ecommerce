package angel_zero.inventario.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTOEditarCategoria(
			@NotBlank @Pattern(regexp = "^[1-9]\\d*$") String id,
			@NotBlank String categoria
		) {

}
