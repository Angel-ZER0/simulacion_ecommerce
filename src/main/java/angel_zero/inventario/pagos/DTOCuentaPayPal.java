package angel_zero.inventario.pagos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeName("PAYPAL") // Indica que esta es la subclase para tipoPago = PAYPAL
public class DTOCuentaPayPal extends DTORegistarMetodoPago {

    @NotBlank
    @Email
    private String email;

    public DTOCuentaPayPal(@JsonProperty("propietarioMetodo") String propietarioMetodo,
                           @JsonProperty("calleFacturacion") String calleFacturacion,
                           @JsonProperty("tipoPago") String tipoPago,
                           @JsonProperty("email") String email) {
        super(propietarioMetodo, calleFacturacion, tipoPago);
        this.email = email;
    }
}
