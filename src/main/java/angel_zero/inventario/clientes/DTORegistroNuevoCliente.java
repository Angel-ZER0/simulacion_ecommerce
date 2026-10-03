package angel_zero.inventario.clientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTORegistroNuevoCliente(
			@NotBlank String nombreCliente,
			@NotBlank String apellidoPaternoCliente,
			String apellidoMaternoCliente,
			@Valid DTOCredenciales credenciales,
			@NotBlank @Pattern(regexp = "^[0-9]{8,20}$") String telefono
		) {

}
