package angel_zero.inventario.direcciones;

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
@Table(name = "alcaldias_municipios")
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadMunicipiosAlcaldias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String abreviacion;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "id_estado")
	private EntidadEstados estado;

	public EntidadMunicipiosAlcaldias(String nombre, String abreviacion, EntidadEstados estado) {
	
		this.nombre = nombre;
		this.abreviacion = abreviacion;
		this.estado = estado;
	}

	
	
}
