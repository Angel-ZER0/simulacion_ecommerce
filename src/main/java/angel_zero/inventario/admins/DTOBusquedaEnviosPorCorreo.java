package angel_zero.inventario.admins;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOBusquedaEnviosPorCorreo(@NotBlank @Email String correo) {
	

}
