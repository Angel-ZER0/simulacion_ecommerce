package angel_zero.inventario.clientes;

public record DTOInformacionCliente(
			String nombreCliente,
			String apellidoPaternoCliente,
			String apellidoMaternoCliente,
			//String correo,
			String telefono
		) {
	
	public DTOInformacionCliente(EntidadClientes cliente) {
		this(
			cliente.getNombreCliente(), 
			cliente.getApellidoPaternoCliente(),
			cliente.getApellidoMaternoCliente(),
			//cliente.getCorreo(), 
			cliente.getTelefono());
	}
	
}
