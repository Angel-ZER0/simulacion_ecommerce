package angel_zero.inventario.direcciones;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class EntidadEstados {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nombreEstado;
	
	@Column(nullable = false)
	private String abreviaturaEstado;
	
	@OneToMany(mappedBy = "estado", cascade = CascadeType.PERSIST)
	private List <EntidadMunicipiosAlcaldias> territorios;

	public EntidadEstados(String nombreEstado, String abreviaturaEstado) {
		
		this.nombreEstado = nombreEstado;
		this.abreviaturaEstado = abreviaturaEstado;
		
	}
	
}
