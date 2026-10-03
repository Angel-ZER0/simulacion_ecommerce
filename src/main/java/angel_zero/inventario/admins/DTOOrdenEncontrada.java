package angel_zero.inventario.admins;

import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;

public record DTOOrdenEncontrada(
		String nombreCliente, 
		String fechaCracionCarrito,
		String fechaFiniquitoOrden,
		String estadoEntrega,
		String estadoTransaccion,
		String montoOrden,
		boolean estadoOrden
	) {
	
	public DTOOrdenEncontrada(EntidadHistorialOrdenes ordenEncontrada) {
		
		this(
		
			ordenEncontrada.getOrdenCliente().getApellidoPaternoCliente() +
			" " +ordenEncontrada.getOrdenCliente().getApellidoMaternoCliente() +
			" " + ordenEncontrada.getOrdenCliente().getNombreCliente(),
			ordenEncontrada.getFechaInicioOrden().toString(),
			ordenEncontrada.getFechaFiniquitpOrden().toString(),
			ordenEncontrada.getEstadoPaquete().getEstado(),
			ordenEncontrada.getEstadoTransaccion().getEstado(),
			ordenEncontrada.getTotalAPagar().toString(),
			ordenEncontrada.isActiva()
			
		);
		
	}

}
