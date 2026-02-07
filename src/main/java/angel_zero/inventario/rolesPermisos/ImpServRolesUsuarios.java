package angel_zero.inventario.rolesPermisos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import angel_zero.inventario.admins.DTOCrearAdmin;
import angel_zero.inventario.admins.ImpServiciosAdmins;
import angel_zero.inventario.clientes.DTORegistroNuevoCliente;
import angel_zero.inventario.clientes.ImplementacionServiciosClientes;
import angel_zero.inventario.proveedores.DTORegistrarProveedor;
import angel_zero.inventario.proveedores.ImpServProveedores;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServRolesUsuarios implements IntServRolesUsuarios {
	
	private final RepositorioRolesUsuarios repoRolUsu;
	private final ImplementacionServiciosClientes servClientes;
	private final ImpServProveedores servProovedores;
	private final ImpServiciosAdmins servAdministradores;

	@Override
	public ResponseEntity registrarUsuario(Object registro) {
		
		String correo;
		
		if (registro instanceof DTORegistroNuevoCliente registroCliente) {
			
			correo = registroCliente.credenciales().correo();
		
			if (repoRolUsu.existsByCorreo(correo)) {
				
				ResponseEntity.badRequest().body("Este correo ya ha sido registrado.");
				
			} 
				
			return servClientes.registrarNuevoCliente(registroCliente);

			
		} else if (registro instanceof DTORegistrarProveedor registroProovedor) {
			
			correo = registroProovedor.credenciales().correo();
			
			if (repoRolUsu.existsByCorreo(correo)) {
				
				ResponseEntity.badRequest().body("Este correo ya ha sido registrado.");
				
			}
			
			return servProovedores.registrarProovedor(registroProovedor);
			
		} else if (registro instanceof DTOCrearAdmin registroAdmin) {
			
			correo = registroAdmin.credenciales().correo();
			
			if (repoRolUsu.existsByCorreo(correo)) {
				
				ResponseEntity.badRequest().body("Este correo ya ha sido registrado.");
				
			}
			
			return servAdministradores.anadirNuevoAdmin(registroAdmin);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No fue posible completar el registro.");
			
		}
		
	}

}
