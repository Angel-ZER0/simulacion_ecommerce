package angel_zero.inventario.direcciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTORegistrarDireccion(
		
			@NotNull String calle,
			@NotNull String colonia,
			@NotNull @Pattern(regexp = "\\d{5}") String codigoPostal,
			@NotNull String municipio,
			@NotNull String estado,
			String referenciasOpcionales,
			@NotNull String longitud,
			@NotNull String latitud
		
		) {

}
