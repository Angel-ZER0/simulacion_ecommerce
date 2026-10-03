package angel_zero.inventario.rolesPermisos;

import org.springframework.http.ResponseEntity;

import angel_zero.inventario.credencialesAcceso.DTOCredencialesAcceso;

public interface IntServRolesUsuarios {
	
	ResponseEntity registrarUsuario(Object credenciales);

}
