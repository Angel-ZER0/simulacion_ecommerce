package angel_zero.inventario.tokenGen;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class ServicioToken {

	@Value("${api.key}")
	private String firmaApi;
	@Value("${api.firma}")
	private String firmante;
	private LocalDateTime duracionToken = LocalDateTime.now().plusDays(1);//.plusHours(2);
	private Date expiracionToken = Date.from(duracionToken.atZone(ZoneId.systemDefault()).toInstant());
	
	private final HttpServletRequest peticion;
	
	public String generacionToken(EntidadRelacionRolesUsuarios usuario) {
		
		if (usuario == null || usuario.getUsername() == null) {
			
			throw new IllegalArgumentException("El usuario no puede ser nulo y debe tener un username válido");
			
		}
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC512(firmaApi);
			return JWT.create()
					.withIssuer(firmante)
					.withSubject(usuario.getUsername())
					.withClaim("id", usuario.getId())
					.withExpiresAt(expiracionToken)
					.sign(algorithm);
			
		} catch (JWTCreationException exception) {
			
			throw new RuntimeException("Error al generar el token: " + exception.getMessage(), exception);
			
		}
		
	}
	
	public String getUsername(String token) {

		if (token == null || token.isEmpty()) {

			throw new IllegalArgumentException("El token no puede estar vacío");

		} else {

			DecodedJWT verificar;

			try {

				Algorithm algorithm = Algorithm.HMAC512(firmaApi);
				verificar = JWT.require(algorithm).withIssuer(firmante).build().verify(token);

			} catch (JWTVerificationException exception) {

				throw new IllegalStateException("Ocurrió un error: " + exception.getMessage(), exception);

			}

			String nombreUsuario = verificar.getSubject();

			if (nombreUsuario == null || nombreUsuario.isEmpty()) {

				throw new IllegalStateException("Error procesando el token");

			}

			return nombreUsuario;

		}

	}
	
	public String conseguirToken() {
		
		if (peticion.getHeader(HttpHeaders.AUTHORIZATION) == null && !peticion.getHeader(HttpHeaders.AUTHORIZATION).startsWith("Bearer ")) {
			
			throw new IllegalArgumentException("El encabezado de autorización no es correcto");
			
		} else {
			
			return peticion.getHeader(HttpHeaders.AUTHORIZATION).substring(7).trim();
			
		}
		
	}
	
}
