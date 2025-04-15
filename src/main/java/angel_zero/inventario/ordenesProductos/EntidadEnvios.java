package angel_zero.inventario.ordenesProductos;

import java.util.List;

import angel_zero.inventario.direcciones.EntidadDirecciones;
import angel_zero.inventario.historialOrdenes.EntidadEstadoEntregaOrden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "envios")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class EntidadEnvios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_direccion_cliente", nullable = false)
	private EntidadDirecciones direccionCliente;

	@ManyToMany
	@JoinTable(name = "envio_direcciones_proveedores", 
		joinColumns = 
			@JoinColumn(name = "id_envio", referencedColumnName = "id", nullable = false), 
		inverseJoinColumns = 
			@JoinColumn(name = "id_direcciones_sucursales", referencedColumnName = "id", nullable = false))
	private List<EntidadDirecciones> direccionesProveedores;

	@ManyToOne
	@JoinColumn(nullable = false)
	private EntidadEstadoEntregaOrden estadoPaquete;

	public EntidadEnvios(EntidadDirecciones direccionCliente, List<EntidadDirecciones> direccionesProveedores,
			EntidadEstadoEntregaOrden estadoEntrega) {
		this.direccionCliente = direccionCliente;
		this.direccionesProveedores = direccionesProveedores;
		this.estadoPaquete = estadoEntrega;
	}

}
