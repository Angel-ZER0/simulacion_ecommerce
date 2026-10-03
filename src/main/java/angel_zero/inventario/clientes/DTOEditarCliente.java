package angel_zero.inventario.clientes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTOEditarCliente(
			@NotNull Long id,
			String nombreCliente,
			String apellidoPaternoClientes,
			String apellidoMaternoCliente,
			String contrasena,
			@Email String correo,
			@Pattern(regexp = "^[0-9]{8,20}$") String telefono
		) {

}
