package angel_zero.inventario.categoria;

import java.util.List;

import angel_zero.inventario.productos.EntidadProductos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadCategoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 255)
	private String categoria;
	
	@Column(length = 255)
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private List <EntidadProductos> listaProductosCategorias;

	public EntidadCategoria(DTONuevaCategoria nuevaCategoria) {
		this.categoria = nuevaCategoria.categoria();
	}
	
	public void actualizarCategoria(DTOEditarCategoria edicionCategoria) {
		
		if (edicionCategoria.categoria() != null) {
			
			this.categoria = edicionCategoria.categoria();
			
		}
		
	}

	public EntidadCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
}
