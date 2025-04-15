package angel_zero.inventario.clientes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOCredenciales(@NotBlank @Email String correo, @NotBlank String contrasena) {

}
