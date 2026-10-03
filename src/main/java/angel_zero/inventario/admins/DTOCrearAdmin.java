package angel_zero.inventario.admins;


import angel_zero.inventario.credencialesAcceso.DTOCredencialesAcceso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DTOCrearAdmin(
			@NotBlank String nombreUsuario,
			@Valid DTOCredencialesAcceso credenciales
		) {

}
