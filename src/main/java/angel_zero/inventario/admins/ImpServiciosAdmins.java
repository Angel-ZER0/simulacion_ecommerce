package angel_zero.inventario.admins;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.DialectOverride.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import angel_zero.inventario.categoria.DTOEditarCategoria;
import angel_zero.inventario.categoria.DTOInformacionCategoria;
import angel_zero.inventario.categoria.DTONuevaCategoria;
import angel_zero.inventario.categoria.EntidadCategoria;
import angel_zero.inventario.categoria.RepositorioCategorias;
import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionCategoria404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionDireccion404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionEnvio404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionEstadoEntrega404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionUsuario404;
import angel_zero.inventario.direcciones.EntidadDirecciones;
import angel_zero.inventario.direcciones.RepositorioDirecciones;
import angel_zero.inventario.historialOrdenes.EntidadEstadoEntregaOrden;
import angel_zero.inventario.historialOrdenes.RepositorioEstadoEntrega;
import angel_zero.inventario.ordenesProductos.DTODatosEnvio;
import angel_zero.inventario.ordenesProductos.DTOInfoPaquete;
import angel_zero.inventario.ordenesProductos.EntidadEnvios;
import angel_zero.inventario.ordenesProductos.FiltroBusquedaEnvios;
import angel_zero.inventario.ordenesProductos.RepositorioEnvios;
import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.productos.EspecificacionProducto;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import angel_zero.inventario.rolesPermisos.ServSelecUsuario;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServiciosAdmins implements IntServiciosAdmins {

	private final RepositorioAdmins repoAdmins;
	private final RepositorioRoles repoRoles;
	private final RepositorioRolesUsuarios repoRolUsu;
	private final PasswordEncoder passwordEncoder;
	private final RepositorioCategorias repoCategorias;
	private final RepositorioEstadoEntrega repoEstadoEntrega;
	private final RepositorioDirecciones repoDirecciones;
	private final RepositorioEnvios repoEnvios;
	private final ServSelecUsuario servSelecUsuario;
	
	private EntidadEnvios localizarEnvioActivo (Long idEnvio) {
		
		EntidadEnvios envioLocalizado = repoEnvios.findById(idEnvio)
				.orElseThrow(() -> new ExcepcionEnvio404("No se ha encontrado envio activo con ese id"));
		return envioLocalizado;
		
	}
	
	private EntidadEnvios localizarEnvioConIdYEstado(Long idEnvio, String estadoPaquete) {
		
		EntidadEnvios envio = repoEnvios.localizarEnvio(idEnvio, estadoPaquete)
				.orElseThrow(() -> new ExcepcionEnvio404("No se encontró envio coincidente con los datos proporcionado."));
		return null;
		
	}
	
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

	@Override
	public ResponseEntity<DTOInfoPaquete> mostrarInfoDireccionDeEnvioActivo(Long idEnvio) {
		/*
		EntidadDirecciones direccionDelEnvio = repoDirecciones.findById(datosEnvio.idDireccion())
				.orElseThrow(ExcepcionDireccion404::new);
		EntidadEnvios envioLocalizado = repoEnvios.encontrarEnvio(datosEnvio.idEnvio(), direccionDelEnvio)
				.orElseThrow(() -> new ExcepcionEnvio404("No hay envio activo para la dirección en la calle " 
						+ direccionDelEnvio.getCalle() + "con código postal: " + direccionDelEnvio.getCodigoPostal()));
		
		return ResponseEntity.ok(new DTOInfoPaquete(direccionDelEnvio));
		*/
		
		EntidadEnvios envio = localizarEnvioActivo(idEnvio);
		
		return ResponseEntity.ok(new DTOInfoPaquete(envio));
		
	}
	
	@Override
	public ResponseEntity<String> cambiarEstadoPaquete(Long idEnvio, Long idEstadoPaquete) {
		
		EntidadEnvios envio = localizarEnvioActivo(idEnvio);
		EntidadEstadoEntregaOrden estadoPaquete = repoEstadoEntrega.findById(idEstadoPaquete)
				.orElseThrow(ExcepcionEstadoEntrega404::new);
		envio.actualizarEstadoPaquete(estadoPaquete);
		
		return ResponseEntity.ok("El estado del paquete con id " + envio.getId() + 
				" se ha actualizado a " + estadoPaquete.getEstado());
		
	}

	@Override
	public ResponseEntity<DTOInfoPaquete> localizarOrden(DTOBusquedaOrden busquedaOrden) {
		
		EntidadEnvios envioLocalizado = localizarEnvioConIdYEstado(busquedaOrden.idEnvio(), busquedaOrden.estadoPaquete());
		return ResponseEntity.ok(new DTOInfoPaquete(envioLocalizado));
		
	}

	@Override
	public ResponseEntity<Page<DTODatosEnvio>> localizarPaquete(FiltroBusquedaEnvios filtro, Pageable paginacion) {
		
		/*
		EntidadRelacionRolesUsuarios usuario = repoRolUsu.findByCorreo(correo);
		
		if (usuario == null || usuario.getCliente() == null) {
			
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("That nigger was not found");
			
		}
		*/
		/*
		List <EntidadEnvios> enviosEncontrados = repoEnvios.encontraPorEmailCliente(correo.correo());
		
		if (enviosEncontrados.isEmpty()) {
			
			return ResponseEntity.notFound().build();
			
		}
		
		return ResponseEntity.ok(enviosEncontrados.stream().map(DTODatosEnvio::new));
		*/
		
		EntidadRelacionRolesUsuarios usuario = repoRolUsu.findByCorreo(filtro.correo())
				.orElseThrow(ExcepcionUsuario404::new);
		
		LocalDate fecha = null;
		
		if (filtro.fechaEntrega() != null) {
			
			try {
				
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				fecha = LocalDate.parse(filtro.fechaEntrega(), formato);
				
			} catch (RuntimeException e) {
				
				ResponseEntity.badRequest().build();
				
			}
			
		}
		
		//LocalDate fecha = LocalDate.parse(filtro.fechaEntrega(), DateTimeFormatter.ISO_DATE);
		
		Specification<EntidadEnvios> spec = EspecificacionEnvios.criteriosFiltrado(
				fecha,
				filtro.estadoEntrega()
		).and((root, query, cb) -> cb.equal(root.get("direccionCliente").get("idCliente")
				.get("rolesCliente"), usuario));
		
		return ResponseEntity.ok(repoEnvios.findAll(spec, paginacion).map(DTODatosEnvio::new));
		
		
		// ResponseEntity.ok(repoEnvios.findAll(, paginacion).mao(DTODatosEnvio::new));
		
	}

}
