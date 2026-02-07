package angel_zero.inventario.ordenesProductos;

public record DTODatosEnvio(
			
			String correoUsuaio,
			String nombreUsuaio,
			String direccionEntrega,
			String fechaEstimadaEnvio,
			String fehcaEntrega,
			String estadoEntrega
		
		) {
	
	public DTODatosEnvio(EntidadEnvios envio){
		
		this(
				
			envio.getDireccionCliente().getIdCliente().getRolesCliente().getCorreo(),
			envio.getDireccionCliente().getIdCliente().getApellidoPaternoCliente() + " " +
			envio.getDireccionCliente().getIdCliente().getNombreCliente(),
			envio.getDireccionCliente().getCalle() + " " + envio.getDireccionCliente().getColonia() + " " +
			envio.getDireccionCliente().getEstado().getNombreEstado() + " " + 
			envio.getDireccionCliente().getCodigoPostal(),
			envio.getFechaEstimadaDeEntrega().toString(),
			envio.getFechaEntrega() != null
            ? envio.getFechaEntrega().toString()
            : "pendiente de entrega",
			envio.getEstadoPaquete().getEstado()
			
		);
		
	}

}
