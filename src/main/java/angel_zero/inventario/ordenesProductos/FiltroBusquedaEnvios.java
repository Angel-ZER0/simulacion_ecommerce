package angel_zero.inventario.ordenesProductos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FiltroBusquedaEnvios(
		
		@NotBlank @Email String correo,
		String estadoEntrega,
		String fechaEntrega
		
		) {

}
