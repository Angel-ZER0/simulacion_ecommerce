package angel_zero.inventario.pagos;

import angel_zero.inventario.clientes.EntidadClientes;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PAYPAL")
public class EntidadCuentaPayPal extends EntidadMetodosPago {

	@Column(name = "correo_asociado_paypal", nullable = true)
	@Email
	private String emailCuentaPayPal;

	public EntidadCuentaPayPal(DTOCuentaPayPal registrarMetodo, boolean metodoPreferido, EntidadClientes idCliente) {
		super(registrarMetodo.getPropietarioMetodo(), registrarMetodo.getCalleFacturacion(), 
				idCliente, metodoPreferido);
		this.emailCuentaPayPal = registrarMetodo.getEmail();
	}
	
	public EntidadCuentaPayPal(String emailCuentaPayPal, String propietarioMetodo, EntidadClientes idCliente, boolean metodoPreferido) {
		
		super(propietarioMetodo, idCliente, metodoPreferido);
		this.emailCuentaPayPal = emailCuentaPayPal;
		
	}

	@Override
	public String getTipoPago() {

		return "PAYPAL";
		
	}
	
	
	
}
