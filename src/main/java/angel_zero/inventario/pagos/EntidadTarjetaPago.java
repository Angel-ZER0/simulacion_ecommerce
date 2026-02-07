package angel_zero.inventario.pagos;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Date;

import angel_zero.inventario.clientes.EntidadClientes;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("TARJETA")
public class EntidadTarjetaPago extends EntidadMetodosPago {

	@Column(nullable = true, length = 16)
	private String numerosTarjeta;
	
	@Column(nullable = true)
	private YearMonth fechaExpiracion;
	
	@Column(nullable = true, length = 3)
	private String cvv;
	
	@Column(nullable = true)
	private BigDecimal saldoDisponible;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_tarjeta", nullable = true)
	private EntidadTipoTarjeta tipoTarjeta;

	public EntidadTarjetaPago(DTODatosTarjeta datosTarjeta, boolean metodoPreferido, EntidadClientes idCliente, 
			YearMonth fechaExpiracion, EntidadTipoTarjeta tipoTarjeta, BigDecimal saldoDisponible) {
		super(datosTarjeta.getPropietarioMetodo(), datosTarjeta.getCalleFacturacion(), idCliente, metodoPreferido);
		this.numerosTarjeta = datosTarjeta.getNumerosTarjeta();
		this.fechaExpiracion = fechaExpiracion;
		this.cvv = datosTarjeta.getCvv();
		this.saldoDisponible = saldoDisponible;
		this.tipoTarjeta = tipoTarjeta;
	}
	
	public EntidadTarjetaPago(String propietarioMetodo, String calleFacturacionMetodo, EntidadClientes idCliente,
			boolean metodoPreferido, String numerosTarjeta, String fechaExpiracion, String cvv,
			BigDecimal saldoDisponible, EntidadTipoTarjeta tipoTarjeta) {
		super(propietarioMetodo, calleFacturacionMetodo, idCliente, metodoPreferido);
		this.numerosTarjeta = numerosTarjeta;
		this.fechaExpiracion = YearMonth.parse(fechaExpiracion);
		this.cvv = cvv;
		this.saldoDisponible = saldoDisponible;
		this.tipoTarjeta = tipoTarjeta;
	}

	@Override
	public String getTipoPago() {
		
		return "TARJETA";
	}
	
	public void actualizarSaldo(BigDecimal montoOrden) {
		
		this.saldoDisponible = saldoDisponible.subtract(montoOrden);
		
	}

}
