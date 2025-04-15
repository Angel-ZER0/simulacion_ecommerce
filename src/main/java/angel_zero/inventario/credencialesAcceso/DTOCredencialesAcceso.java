package angel_zero.inventario.credencialesAcceso;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOCredencialesAcceso(@NotBlank @Email String correo, @NotBlank String contrasena) {

}
