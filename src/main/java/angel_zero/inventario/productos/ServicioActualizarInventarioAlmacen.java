package angel_zero.inventario.productos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import angel_zero.inventario.ordenesProductos.RepositorioOrdenesProductos;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.ordenesProductos.EntidadOrdenesProductos;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioActualizarInventarioAlmacen {

	//private RepositorioProductos repoProductos;
	private final RepositorioOrdenesProductos repoOrdenes;
	
	public void actualizarInventario(EntidadHistorialOrdenes listaProductosOrdenados) {
		
		List <EntidadOrdenesProductos> ordenProductos = repoOrdenes.productosEnCarrito(listaProductosOrdenados);
		
		if (ordenProductos == null || ordenProductos.isEmpty()) {
			
			throw new IllegalArgumentException("No fue encontrada o no existen productos en la orden procesada");
			
		}
		
		for (EntidadOrdenesProductos producto : ordenProductos) {
			
			EntidadProductos productoSolicitado = producto.getProductoSolicitado();
			int cantidadSolicitada = producto.getCantidad();
			
			if (productoSolicitado.getCantidadDisponible() < cantidadSolicitada) {
				
				throw new IllegalStateException("Existencias insuficientes del producto " + productoSolicitado.getNombreProducto() + 
						" para suplir la orden.");
				
			}
			
			productoSolicitado.actualizarCantidadPorCompra(productoSolicitado.getCantidadDisponible(), cantidadSolicitada);
			
			
		}
		
	}
	
}
