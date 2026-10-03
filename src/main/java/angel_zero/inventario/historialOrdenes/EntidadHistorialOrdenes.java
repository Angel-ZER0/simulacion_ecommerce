package angel_zero.inventario.historialOrdenes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.ordenesProductos.EntidadEnvios;
import angel_zero.inventario.ordenesProductos.EntidadOrdenesProductos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "historial_ordenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadHistorialOrdenes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime fechaInicioOrden;
	
	@Column(nullable = true)
	private LocalDateTime fechaFiniquitpOrden;
	
	@ManyToOne
	@JoinColumn(name = "orden_cliente", nullable = false)
	private EntidadClientes ordenCliente;
	
	@OneToMany(mappedBy = "idOrdenHistorial")
	private List <EntidadOrdenesProductos> numeroDeOrden;
	
	@Column//(nullable = false)
	private BigDecimal totalAPagar;
	
	@Column//(nullable = false)
	private boolean activa;
	
	@ManyToOne
	@JoinColumn(name = "estado_transaccion")
	private EntidadEstadoPagoOrden estadoTransaccion;
	
	@ManyToOne()
	@JoinColumn(name = "estado_paquete", nullable = true)
	private EntidadEstadoEntregaOrden estadoPaquete;

	public EntidadHistorialOrdenes(EntidadClientes cliente) {
		this.fechaInicioOrden = LocalDateTime.now();
		this.ordenCliente = cliente;
		this.activa = true;
	}
	
	public EntidadHistorialOrdenes(EntidadClientes cliente, EntidadOrdenesProductos anadirOrden) {
		
		this.ordenCliente = cliente;
		this.fechaInicioOrden = LocalDateTime.now();
		this.numeroDeOrden.add(anadirOrden);
		this.totalAPagar = calcularTotalAPagar();
		
	}
	
    // Método que calcula el total a pagar sumando el precio de cada producto en la lista numeroDeOrden
    public BigDecimal calcularTotalAPagar() {
        totalAPagar = numeroDeOrden.stream()
            .map(EntidadOrdenesProductos::getPrecio)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAPagar;
    }

    // Getters y setters (incluyendo el getter para totalAPagar que llama a calcularTotalAPagar)
    public BigDecimal getTotalAPagar() {
        return calcularTotalAPagar();
    }

    public void setNumeroDeOrden(List<EntidadOrdenesProductos> numeroDeOrden) {
        this.numeroDeOrden = numeroDeOrden;
        calcularTotalAPagar();  // Actualiza totalAPagar cuando se cambia la lista de productos
    }

    public void actualizarEstadoEntrega(EntidadEnvios envio) {
    	
    	this.estadoPaquete = envio.getEstadoPaquete();
    	
    }
    
    public void actualizarEstadoOrden(EntidadEstadoPagoOrden estadoPago) {
    	
    	if (estadoPago != null) {
    		
    		this.activa = false;
    		this.estadoTransaccion = estadoPago;
    		this.fechaFiniquitpOrden = LocalDateTime.now();
    		
    	} 
    	
    }

	public EntidadHistorialOrdenes(LocalDateTime fechaInicioOrden,
			EntidadClientes ordenCliente, boolean activa, 
			List <EntidadOrdenesProductos> numeroDeOrden,
			EntidadEstadoPagoOrden estadoTransaccion) {
		this.fechaInicioOrden = fechaInicioOrden;
		this.ordenCliente = ordenCliente;
		this.totalAPagar = calcularTotalAPagar();
		this.activa = activa;
		this.numeroDeOrden = numeroDeOrden;
		this.estadoTransaccion = estadoTransaccion;
	}
    
    

}
