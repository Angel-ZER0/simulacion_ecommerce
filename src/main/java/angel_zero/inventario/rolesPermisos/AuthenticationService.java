package angel_zero.inventario.rolesPermisos;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

	private final RepositorioRolesUsuarios repoRolUsu;
	/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		return repoRolUsu.findByCorreo(username);
		
	}
	*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		return repoRolUsu.buscarUsuario(username);
		
	}
	/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		final UserDetails usuario = repoRolUsu.buscarUsuario(username);
		return usuario;
		
	}
	*/
}
