package angel_zero.inventario.admins;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import angel_zero.inventario.categoria.DTOEditarCategoria;
import angel_zero.inventario.categoria.DTOInformacionCategoria;
import angel_zero.inventario.categoria.DTONuevaCategoria;
import angel_zero.inventario.ordenesProductos.DTODatosEnvio;
import angel_zero.inventario.ordenesProductos.DTOInfoPaquete;
import angel_zero.inventario.ordenesProductos.FiltroBusquedaEnvios;

public interface IntServiciosAdmins {

	ResponseEntity anadirNuevoAdmin (DTOCrearAdmin nuevoAdmin);
	
	ResponseEntity <DTOInformacionCategoria> crearCategoria(DTONuevaCategoria nuevaCategoria);
	
	ResponseEntity <Page<DTOInformacionCategoria>> listarCategorias(Pageable paginacion);
	
	ResponseEntity <DTOInformacionCategoria> modificarCategoria(DTOEditarCategoria editarCategoria);
	
	ResponseEntity <String> eliminarCategoria(Long idCategoria);
	
	ResponseEntity <DTOInfoPaquete> mostrarInfoDireccionDeEnvioActivo(Long idEnvio);
	
	ResponseEntity <String> cambiarEstadoPaquete(Long idEnvio, Long idEstadoPaquete);
	
	ResponseEntity <DTOInfoPaquete> localizarOrden(DTOBusquedaOrden busquedaOrden);
	
	ResponseEntity <Page<DTODatosEnvio>> localizarPaquete(FiltroBusquedaEnvios filtro, Pageable paginacion);
	
}
