package angel_zero.inventario.proveedores;


import angel_zero.inventario.credencialesAcceso.DTOCredencialesAcceso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTORegistrarProveedor(
		@NotBlank String nombreEmpresa,
		@NotBlank @Pattern(regexp = "^[0-9]{8,20}$") String telefono,
		@Valid DTOCredencialesAcceso credenciales
		) {

	
	
}
