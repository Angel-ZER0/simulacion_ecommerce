package angel_zero.inventario.historialOrdenes;

import java.util.List;

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
@Table(name = "estados_pago")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class EntidadEstadoPagoOrden {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String estado;
	
	@OneToMany(mappedBy = "estadoTransaccion")
	//@JoinColumn(nullable = false)
	private List <EntidadHistorialOrdenes> estadoPago;

	public EntidadEstadoPagoOrden(String estado) {
		this.estado = estado;
	}

}
