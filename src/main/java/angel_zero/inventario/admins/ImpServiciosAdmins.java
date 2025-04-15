package angel_zero.inventario.admins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import angel_zero.inventario.categoria.DTOEditarCategoria;
import angel_zero.inventario.categoria.DTOInformacionCategoria;
import angel_zero.inventario.categoria.DTONuevaCategoria;
import angel_zero.inventario.categoria.EntidadCategoria;
import angel_zero.inventario.categoria.RepositorioCategorias;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionCategoria404;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServiciosAdmins implements IntServiciosAdmins {

	private final RepositorioAdmins repoAdmins;
	private final RepositorioRoles repoRoles;
	private final RepositorioRolesUsuarios repoRolUsu;
	private final PasswordEncoder passwordEncoder;
	private final RepositorioCategorias repoCategorias;
	
	@Override
	public ResponseEntity anadirNuevoAdmin(DTOCrearAdmin nuevoAdmin) {
		
		if (repoAdmins.existsByNombreUsuario(nuevoAdmin.nombreUsuario())) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un administrador con ese usuario.");
			
		}
		
		EntidadRoles rolCliente = repoRoles.findByNombreRol("cliente");
		EntidadRoles rolProovedor = repoRoles.findByNombreRol("proovedor");
		EntidadRoles rolAdmin = repoRoles.findByNombreRol("administrador");
		
		List <EntidadRoles> roles = new ArrayList <EntidadRoles>();
		
		roles.add(rolCliente);
		roles.add(rolProovedor);
		roles.add(rolAdmin);
		
		EntidadAdmins registroNuevoAdmin = repoAdmins.save(new EntidadAdmins(nuevoAdmin));
		
		EntidadRelacionRolesUsuarios asignacionRoles = 
				repoRolUsu.save(new EntidadRelacionRolesUsuarios(registroNuevoAdmin, roles, nuevoAdmin.credenciales().correo(),
						passwordEncoder.encode(nuevoAdmin.credenciales().contrasena())));
		
		return ResponseEntity.ok(new DTOInformacionAdmin(registroNuevoAdmin));
		
	}
	
	@Override
	public ResponseEntity <DTOInformacionCategoria> crearCategoria(DTONuevaCategoria nuevaCategoria) {
		
		EntidadCategoria categoria = repoCategorias.save(new EntidadCategoria(nuevaCategoria));
		return ResponseEntity.ok(new DTOInformacionCategoria(categoria));
		
	}

	@Override
	public ResponseEntity <DTOInformacionCategoria> modificarCategoria(DTOEditarCategoria edicionCategoria) {
	
		EntidadCategoria categoria = repoCategorias.findById(Long.valueOf(edicionCategoria.id()))
				.orElseThrow(ExcepcionCategoria404::new);
		categoria.actualizarCategoria(edicionCategoria);
		return ResponseEntity.ok(new DTOInformacionCategoria(categoria));
			
	}

	@Override
	public ResponseEntity <Page<DTOInformacionCategoria>> listarCategorias(Pageable paginacion) {
		
		return ResponseEntity.ok(repoCategorias.findAll(paginacion).map(DTOInformacionCategoria::new));
		
	}

	@Override
	public ResponseEntity<String> eliminarCategoria(Long idCategoria) {
		
	    if (!repoCategorias.existsById(idCategoria)) {
	    	
	        throw new ExcepcionCategoria404();
	        
	    }
	    
	 
	    repoCategorias.deleteById(idCategoria);
	    return ResponseEntity.noContent().build();
	}
	
}
