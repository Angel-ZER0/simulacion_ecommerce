package angel_zero.inventario.marcas;

import java.util.List;

import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.proveedores.EntidadProveedores;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marcas")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EntidadMarcas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "proovedorDeMarca", nullable = false)
	private EntidadProveedores proovedorMarca;
	
	@Column(unique = true, length = 255, nullable = false)
	private String marca;
	
	@Column(length = 255)
	@OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
	private List <EntidadProductos> listaProductos;

	public EntidadMarcas(DTOMarcaAnadida nuevaMarca, EntidadProveedores duenoMarca) {
		this.marca = nuevaMarca.marca();
		this.proovedorMarca = duenoMarca;
	}
	
	public void actualizarMarca(DTOMarcaAnadida actualizarMarca) {
		
		if (actualizarMarca.marca() != null) {
		
			this.marca = actualizarMarca.marca();
			
		}
		
	}

	public EntidadMarcas(EntidadProveedores proovedorMarca, String marca) {
		this.proovedorMarca = proovedorMarca;
		this.marca = marca;
	}
	
}
