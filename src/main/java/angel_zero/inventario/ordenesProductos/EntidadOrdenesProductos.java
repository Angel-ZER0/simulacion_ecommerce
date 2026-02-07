package angel_zero.inventario.ordenesProductos;

import java.math.BigDecimal;
import java.util.List;

import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.productos.DTODetalleCompra;
import angel_zero.inventario.productos.EntidadProductos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ordenes_productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadOrdenesProductos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	private EntidadProductos productoSolicitado;

	@ManyToOne
	@JoinColumn(name = "orden_id_historial", nullable = true)
	private EntidadHistorialOrdenes idOrdenHistorial;

	@Column(nullable = false)
	private int cantidad;
	
	@Column(nullable = false)
	private BigDecimal precio;
	
	public EntidadOrdenesProductos (
			
			EntidadProductos producto, 
			EntidadHistorialOrdenes idOrdenHistorial,
			DTODetalleCompra detalleCompra,
			BigDecimal cantidadAPagar
			
		) {
		
		this.productoSolicitado = producto;
		this.idOrdenHistorial = idOrdenHistorial;
		this.cantidad = detalleCompra.cantidad();
		this.precio = cantidadAPagar;
		
	}
	
	public EntidadOrdenesProductos(DTOCrearOrden productoOrdenado, EntidadProductos producto) {
		
		this.productoSolicitado = producto;
		this.cantidad = productoOrdenado.cantidad();
		this.precio = BigDecimal.valueOf(producto.getPrecio() * productoOrdenado.cantidad());
		
	}

	public EntidadOrdenesProductos(EntidadProductos productoSolicitado,
			int cantidad) {
		this.productoSolicitado = productoSolicitado;
		this.cantidad = cantidad;
		this.precio = BigDecimal.valueOf(productoSolicitado.getPrecio() * cantidad);
	}
	
	public EntidadOrdenesProductos(EntidadHistorialOrdenes idHistorialOrden) {
	
		this.idOrdenHistorial = idHistorialOrden;
		
	}

}
