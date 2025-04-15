package angel_zero.inventario.admins;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import angel_zero.inventario.categoria.DTOEditarCategoria;
import angel_zero.inventario.categoria.DTOInformacionCategoria;
import angel_zero.inventario.categoria.DTONuevaCategoria;
import angel_zero.inventario.credencialesAcceso.DTOCredencialesAcceso;
import angel_zero.inventario.credencialesAcceso.ServicioAccesoUsuarios;
import angel_zero.inventario.rolesPermisos.ImpServRolesUsuarios;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administracion")
public class ControladorAdmins {

	private final ImpServiciosAdmins servicioAdmins;
	private final ServicioAccesoUsuarios servicioAcceso;
	private final ImpServRolesUsuarios servRolUsu;
	
	@PostMapping("/registrar-administrador")
	ResponseEntity registrarNuevoAdministrador(@RequestBody @Valid DTOCrearAdmin nuevoAdmin) {
		
		return servRolUsu.registrarUsuario(nuevoAdmin);
		
	}
	
	@PostMapping("/inicio-sesion")
	ResponseEntity accesoAdministradores (@RequestBody @Valid DTOCredencialesAcceso credencialesacceso) {
		
		return servicioAcceso.accesoUsuario(credencialesacceso);
		
	}
	
	@PostMapping("/categorias")
	ResponseEntity <DTOInformacionCategoria> registrarNuevaCategoría(@RequestBody @Valid DTONuevaCategoria nuevaCategoria) {
		
		return servicioAdmins.crearCategoria(nuevaCategoria);
		
	}
	
	@GetMapping("/categorias")
	ResponseEntity <Page<DTOInformacionCategoria>> listaCategorias (Pageable paginacion) {
		
		return servicioAdmins.listarCategorias(paginacion);
		
	}
	
	@Transactional
	@PutMapping("/categorias")
	ResponseEntity <DTOInformacionCategoria> modificarCategoria(@RequestBody @Valid DTOEditarCategoria edicionCategoria) {
		
		return servicioAdmins.modificarCategoria(edicionCategoria);
		
	}
	
	@Transactional
	@DeleteMapping("/categorias")
	ResponseEntity <String> eliminarCategoria (Long idCategoria) {
		
		return servicioAdmins.eliminarCategoria(idCategoria);
		
	}
	
}
