package angel_zero.inventario.direcciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionDireccion404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionEstado404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionMunicipio404;
import angel_zero.inventario.historialOrdenes.RepositorioHistorialOrdenes;
import angel_zero.inventario.proveedores.EntidadProveedores;
import angel_zero.inventario.rolesPermisos.ServSelecUsuario;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServDirecciones implements IntServDirecciones{
	
	private final RepositorioEstados repoEstados;
	private final RepositorioMunicipiosAlcaldias repoDemarcaciones;
	private final RepositorioDirecciones repoDirecciones;
	private final ServSelecUsuario servSelecUsuario;
	private final RepositorioHistorialOrdenes repoHistorial;

	@Override
	public ResponseEntity <DTOInfoDireccion> anadirDireccionCliente(DTORegistrarDireccion registroDireccion) {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		boolean direccionPreferida = !repoDirecciones.existsByIdCliente(cliente);
		
		EntidadMunicipiosAlcaldias demarcacion = repoDemarcaciones.encontrarMunicipio(registroDireccion.municipio())
				.orElseThrow(() -> new ExcepcionMunicipio404(registroDireccion.municipio()));
		EntidadEstados estado = repoEstados.encontrarEstadoPorCadena(registroDireccion.estado()).orElseThrow(
				() -> new ExcepcionEstado404("Chingaste a tu madre pá " + registroDireccion.estado()));
		
		if (demarcacion.getEstado() != estado) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
			
		EntidadDirecciones direccion = repoDirecciones.save(new EntidadDirecciones(registroDireccion, direccionPreferida, 
				demarcacion, estado, cliente));
		return ResponseEntity.status(HttpStatus.CREATED).body(new DTOInfoDireccion(direccion));
		
	}

	@Override
	public ResponseEntity<DTOInfoDireccion> anadirDireccionProveedor(DTORegistrarDireccion registroDireccion) {
		
		EntidadMunicipiosAlcaldias demarcacion = repoDemarcaciones.encontrarMunicipio(registroDireccion.municipio())
				.orElseThrow(() -> new ExcepcionMunicipio404(registroDireccion.municipio()));
		EntidadEstados estado = repoEstados.encontrarEstadoPorCadena(registroDireccion.estado()).orElseThrow(
				() -> new ExcepcionEstado404("Chingaste a tu madre pá " + registroDireccion.estado()));
		
		if (demarcacion.getEstado() != estado) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
		
		EntidadDirecciones direccion = repoDirecciones.save(new EntidadDirecciones(registroDireccion, 
			demarcacion, estado, (EntidadProveedores) servSelecUsuario.obtenerEntidadRelacionada()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new DTOInfoDireccion(direccion));
	
	}
	
	@Override
	public ResponseEntity <Page<DTOInfoDireccion>> listarDireccionesClientes(Pageable paginacion) {
			
		return ResponseEntity.ok(repoDirecciones.findByIdCliente((EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada() 
				, paginacion)
				.map(DTOInfoDireccion::new));
		
	}
	
	@Override
	public ResponseEntity<Page<DTOInfoDireccion>> listarDireccionesProveedores(Pageable paginacion) {
		
		return ResponseEntity.ok(repoDirecciones.findByIdProveedor((EntidadProveedores) servSelecUsuario.obtenerEntidadRelacionada() , paginacion)
				.map(DTOInfoDireccion::new));
		
	}

	@Override
	public ResponseEntity<DTOInfoDireccion> editarDireccionCliente(Long idDireccion,
			DTOEdicionDireccion edicionDireccion) {
		
		EntidadDirecciones direccion = repoDirecciones.findByIdAndIdCliente(idDireccion, (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada())
				.orElseThrow(ExcepcionDireccion404::new);
		direccion.actualizarDireccion(edicionDireccion);
		
		if (edicionDireccion.municipio() != null) {
			
			EntidadMunicipiosAlcaldias municipio = repoDemarcaciones.encontrarMunicipio(edicionDireccion.municipio())
					.orElseThrow(() -> new ExcepcionMunicipio404(edicionDireccion.municipio()));
			direccion.actualizarMunicipio(municipio);
			
		}
		
		return ResponseEntity.ok(new DTOInfoDireccion(direccion));
		
	}

	@Override
	public ResponseEntity<DTOInfoDireccion> editarDireccionProveedor(Long idDireccion,
			DTOEdicionDireccion edicionDireccion) {
		
		EntidadDirecciones direccion = repoDirecciones.findByIdAndIdProveedor(idDireccion, (EntidadProveedores) servSelecUsuario.obtenerEntidadRelacionada())
				.orElseThrow(ExcepcionDireccion404::new);
		direccion.actualizarDireccion(edicionDireccion);
		
		if (edicionDireccion.municipio() != null) {
			
			EntidadMunicipiosAlcaldias municipio = repoDemarcaciones.encontrarMunicipio(edicionDireccion.municipio())
					.orElseThrow(() -> new ExcepcionMunicipio404(edicionDireccion.municipio()));
			direccion.actualizarMunicipio(municipio);
			
		}
		
		return ResponseEntity.ok(new DTOInfoDireccion(direccion));
	}

	@Override
	public ResponseEntity<DTOInfoDireccion> eliminarDireccionCliente(Long idDireccion) {
		
		EntidadDirecciones direccion = repoDirecciones.findByIdAndIdCliente(idDireccion, (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada())
				.orElseThrow(ExcepcionDireccion404::new);
		repoDirecciones.deleteById(direccion.getId());
		
		return ResponseEntity.noContent().build();
		
	}

	@Override
	public ResponseEntity<DTOInfoDireccion> eliminarDireccionProovedor(Long idDireccion) {
		
		EntidadDirecciones direccion = repoDirecciones.findByIdAndIdProveedor(idDireccion, (EntidadProveedores) servSelecUsuario.obtenerEntidadRelacionada())
				.orElseThrow(ExcepcionDireccion404::new);
		repoDirecciones.deleteById(direccion.getId());
		
		return ResponseEntity.noContent().build();
		
	}

	@Override
	public ResponseEntity<String> seleccionarSucursalesParaOrden(Long idDireccio) {
		
		return null;
	}

}
