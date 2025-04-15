package angel_zero.inventario.productos;

import angel_zero.inventario.categoria.EntidadCategoria;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.marcas.EntidadMarcas;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EntidadProductos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String nombreProducto;
	
	@Column(nullable = false)
	private double precio;
	
	@Column(nullable = false)
	private int cantidadDisponible;
	
	@ManyToOne
	@JoinColumn(name = "marca", nullable = false)
	private EntidadMarcas marca;
	
	@ManyToOne
	@JoinColumn(name = "categoria", nullable = false)
	private EntidadCategoria categoria;

	public EntidadProductos(DTOCrearProducto nuevoProducto, EntidadMarcas marca, EntidadCategoria categoria) {
		this.nombreProducto = nuevoProducto.nombreProducto();
		this.precio = Double.parseDouble(nuevoProducto.precio());
		this.cantidadDisponible = Integer.parseInt(nuevoProducto.cantidadDisponible());
		this.marca = marca;
		this.categoria = categoria;
	}
	/*
	private void actualizarProducto (DTOActualizarProducto actualizarProducto) {
		
		if (actualizarProducto.nombreProducto() != null) {
			
			this.nombreProducto = actualizarProducto.nombreProducto();
			
		}
		
		if (!Double.isNaN(actualizarProducto.precio())) {
			
			this.precio = actualizarProducto.precio();
			
		}
		
		if (actualizarProducto.cantidadDisponible() > 0) {
			
			this.cantidadDisponible = actualizarProducto.cantidadDisponible();
			
		}
		
	}
	*/
	public void actualizarNombreProducto (DTOActualizarProducto actualizarProducto) {
		
		this.nombreProducto = actualizarProducto.nombreProducto();
		
	}
	
	public void actualizarPrecio (DTOActualizarProducto actualizarProducto) {
		
		this.precio = actualizarProducto.precio();
		
	}
	
	public void actualizarCantidadPorProveedor (DTOActualizarProducto actualizarProducto) {
		
		this.cantidadDisponible = actualizarProducto.cantidadDisponible();
		
	}
	
	public void actualizarMarca (EntidadMarcas marca) {
		
		this.marca = marca;
		
	}
	
	public void actualizarCategoria (EntidadCategoria categoria) {
		
		this.categoria = categoria;
		
	}
	
	public void actualizarCantidadPorCompra(int cantidadActual, int cantidadSolicitada) {
		
		this.cantidadDisponible = cantidadActual - cantidadSolicitada;
		
	}
	
	public EntidadProductos(String nombreProducto, double precio, int cantidadDisponible, EntidadMarcas marca,
			EntidadCategoria categoria) {
		super();
		this.nombreProducto = nombreProducto;
		this.precio = precio;
		this.cantidadDisponible = cantidadDisponible;
		this.marca = marca;
		this.categoria = categoria;
	}
	
}
