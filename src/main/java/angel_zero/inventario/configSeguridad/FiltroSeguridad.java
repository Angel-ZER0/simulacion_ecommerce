package angel_zero.inventario.configSeguridad;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import angel_zero.inventario.tokenGen.ServicioToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiltroSeguridad extends OncePerRequestFilter {

	private final ServicioToken servicioToken;
	private final RepositorioRolesUsuarios repoRolUsu;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			
			String cabeceraAutorizacion = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			if (cabeceraAutorizacion != null && cabeceraAutorizacion.startsWith("Bearer ")) {
				
				String token = cabeceraAutorizacion.replace("Bearer ", "");
				
				String username = servicioToken.getUsername(token);

				if (username != null) {
					
					EntidadRelacionRolesUsuarios usuario = (EntidadRelacionRolesUsuarios) repoRolUsu.buscarUsuario(username);
					var permisos = usuario.getRolesUsuario().stream()
							.map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombreRol())).toList();
					
					/*
					var permisosUsuario = repoRolUsu.permisosUsuario(usuario.getId()).stream()
							.map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombreRol())).toList();
					*/
					var authentication = new UsernamePasswordAuthenticationToken(usuario, null, permisos);
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				}
				
			}
			
			
		} catch (Exception e) {
			
			logger.error("Error procesando el token: " + e.getMessage());
			
		}
		
		//System.out.println(request.getRequestURI());
		filterChain.doFilter(request, response);

	}

}
