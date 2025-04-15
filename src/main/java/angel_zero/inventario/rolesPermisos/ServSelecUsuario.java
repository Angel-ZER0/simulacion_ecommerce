package angel_zero.inventario.rolesPermisos;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import angel_zero.inventario.admins.RepositorioAdmins;
import angel_zero.inventario.clientes.RepositorioClientes;
import angel_zero.inventario.proveedores.RepositorioProveedores;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServSelecUsuario {
	
	private final RepositorioClientes repoClientes;
	private final RepositorioProveedores repoProovedores;
	private final RepositorioAdmins repoAdmins;
	private final RepositorioRolesUsuarios repoRolUsu;
	
	public Object obtenerEntidadRelacionada() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null || !auth.isAuthenticated()) {
			
			throw new SecurityException("No se obtuvo la autenticación del contexto de seguridad");
			
		}

		String correo = auth.getName();

		if (correo.isBlank() || correo == null) {

			throw new IllegalArgumentException("El correo es nulo o está vacío");

		} else {

			EntidadRelacionRolesUsuarios registro = repoRolUsu.findByCorreo(correo);

			if (registro.getCliente() != null) {

				return repoClientes.findById(registro.getCliente().getId())
						.orElseThrow(() -> new IllegalArgumentException("No se encontraron cooincidencias"));

			} else if (registro.getProovedor() != null) {

				return repoProovedores.findById(registro.getProovedor().getId())
						.orElseThrow(() -> new IllegalArgumentException("No se encontraron cooincidencias"));

			} else if (registro.getAdmin() != null) {

				return repoAdmins.findById(registro.getAdmin().getId())
						.orElseThrow(() -> new IllegalArgumentException("No se encontraron cooincidencias"));

			} else {

				throw new IllegalArgumentException("El registro no está relacionado con ninguna entidad");

			}

		}
		
	}
	
}
