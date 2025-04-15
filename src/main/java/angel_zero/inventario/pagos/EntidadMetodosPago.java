package angel_zero.inventario.pagos;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.Optional;

import angel_zero.inventario.clientes.EntidadClientes;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "metodos_pago_usuario")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pago", discriminatorType = DiscriminatorType.STRING)
public abstract class EntidadMetodosPago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String propietarioMetodo;
	
	@Column(nullable = false)
	private String calleFacturacionMetodo;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private EntidadClientes idCliente;
	
	@Column(nullable = false)
	private boolean metodoPreferido;
	
	public abstract String getTipoPago();

	public EntidadMetodosPago(String propietarioMetodo, String calleFacturacionMetodo, 
			EntidadClientes idCliente, boolean metodoPreferido) {
		this.propietarioMetodo = propietarioMetodo;
		this.calleFacturacionMetodo = calleFacturacionMetodo;
		this.idCliente = idCliente;
		this.metodoPreferido = metodoPreferido;
	}

	/*
	public void verificarExpiracion(YearMonth fecha) {
		
		if (fecha.isBefore(YearMonth.now())) {
			
			throw new IllegalArgumentException("La tarjeta ha expirado");
			
		}
		
	}
	
	public void verificarFecha(String mes, String ano) {
		
		if (Integer.valueOf(mes) <= 0 || Integer.valueOf(mes) > 12) {
			
			throw new IllegalArgumentException("Mes no válido");
			
		}
		
		if (Integer.valueOf(ano) > Integer.valueOf(Year.now().toString()) + 8) {
			
			throw new IllegalArgumentException("Año no válido");
		}
		
	}
	
	public YearMonth datosAYearMonth(String mes, String ano) {
		
		YearMonth fechaExpiración = YearMonth.of(Integer.valueOf(mes), Integer.valueOf(ano));
		
		return fechaExpiración;
		
	}

	public EntidadMetodosPago registrarMetodoTarjeta(EntidadClientes idCliente, DTORegistarMetodoPago registrarMetodo,
			EntidadTipoTarjeta tipoTarjeta, BigDecimal saldoDisponible) {
		
		EntidadMetodosPago metodoPago;
		
		if ("TARJETA".equals(registrarMetodo.getTipoPago())) {
			
			if (!(registrarMetodo instanceof DTODatosTarjeta)) {
				
				throw new IllegalArgumentException("Los datos de la tarjeta no son válidos.");
				
			}
			
			DTODatosTarjeta datosTarjeta = (DTODatosTarjeta) registrarMetodo;
			verificarFecha(datosTarjeta.getMesExpliracion(), datosTarjeta.getAnoExpiracion());
			YearMonth fechaTarjeta = datosAYearMonth(datosTarjeta.getMesExpliracion(), datosTarjeta.getAnoExpiracion());
			verificarExpiracion(fechaTarjeta);
			EntidadTarjetaPago registrarTarjeta = new EntidadTarjetaPago(datosTarjeta, idCliente, fechaTarjeta, tipoTarjeta,
					saldoDisponible);
			
			metodoPago = registrarTarjeta;
			
		} else {
			
			throw new IllegalArgumentException("Datos de tarjeta inválidos");
			
		}
		
		return metodoPago;
		
	}
	
	public EntidadMetodosPago registrarMetodoPayPal(EntidadClientes idCliente, DTORegistarMetodoPago registrarMetodo) {
		
		EntidadMetodosPago metodoPago;
		
		if ("PAYPAL".equals(registrarMetodo.getTipoPago())) {
			
			if (!(registrarMetodo instanceof DTOCuentaPayPal)) {
				
				throw new IllegalArgumentException("Los datos de la cuenta no son válidos.");
				
			}
			
			DTOCuentaPayPal datosCuentaPayPal = (DTOCuentaPayPal) registrarMetodo;
			
			EntidadCuentaPayPal cuentaPayPal = new EntidadCuentaPayPal(datosCuentaPayPal, idCliente);
			
			metodoPago = cuentaPayPal;
			
		} else {
			
			throw new IllegalArgumentException("Datos de cuenta incorrectos");
			
		}	
		
		return metodoPago;
		
	}
	*/
}
