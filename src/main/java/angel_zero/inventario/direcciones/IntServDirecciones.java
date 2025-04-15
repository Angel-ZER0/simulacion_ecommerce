package angel_zero.inventario.direcciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IntServDirecciones {

	ResponseEntity <DTOInfoDireccion> anadirDireccionCliente(DTORegistrarDireccion registroDireccion);
	
	ResponseEntity <DTOInfoDireccion> anadirDireccionProveedor(DTORegistrarDireccion registroDireccion);
	
	ResponseEntity <Page<DTOInfoDireccion>> listarDireccionesClientes(Pageable paginacion);
	
	ResponseEntity <Page<DTOInfoDireccion>> listarDireccionesProveedores(Pageable paginacion);
	
	ResponseEntity <DTOInfoDireccion> editarDireccionCliente(Long idDireccion, DTOEdicionDireccion edicionDireccion);
	
	ResponseEntity <DTOInfoDireccion> editarDireccionProveedor(Long idDireccion, DTOEdicionDireccion edicionDireccion);
	
	ResponseEntity <DTOInfoDireccion> eliminarDireccionCliente(Long idDireccion);
	
	ResponseEntity <DTOInfoDireccion> eliminarDireccionProovedor(Long idDireccion);
	
	ResponseEntity <String> seleccionarSucursalesParaOrden(Long idDireccio);
	
}
