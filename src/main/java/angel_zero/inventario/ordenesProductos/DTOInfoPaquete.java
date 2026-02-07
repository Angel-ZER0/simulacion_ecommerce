package angel_zero.inventario.ordenesProductos;

import angel_zero.inventario.direcciones.DTOInfoDireccion;
import angel_zero.inventario.direcciones.EntidadDirecciones;

public record DTOInfoPaquete(
		String nombreCliente,
		DTOInfoDireccion infoDireccion,
		String estadoEnvio
	) {

	public DTOInfoPaquete(EntidadEnvios infoEnvio) {
		
		this(infoEnvio.getDireccionCliente().getIdCliente().getNombreCliente() + " " + 
				infoEnvio.getDireccionCliente().getIdCliente().getApellidoPaternoCliente(),
				new DTOInfoDireccion(infoEnvio.getDireccionCliente()),
				infoEnvio.getEstadoPaquete().getEstado()
				);
		
	}
	
}
