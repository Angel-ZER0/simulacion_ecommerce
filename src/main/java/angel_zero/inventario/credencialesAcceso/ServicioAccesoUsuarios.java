package angel_zero.inventario.credencialesAcceso;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.tokenGen.ServicioToken;
import angel_zero.inventario.tokenGen.TokenGenerado;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioAccesoUsuarios {
	
	private final ServicioToken servicioToken;
	private final AuthenticationManager autenticacion;

	public ResponseEntity accesoUsuario(DTOCredencialesAcceso credenciales) {
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(credenciales.correo(), 
				credenciales.contrasena());
		var autenticarUsuario = autenticacion.authenticate(authToken);
		String JWTToken = servicioToken.generacionToken((EntidadRelacionRolesUsuarios) autenticarUsuario.getPrincipal());
		return ResponseEntity.ok(new TokenGenerado(JWTToken));
		
	}

}
