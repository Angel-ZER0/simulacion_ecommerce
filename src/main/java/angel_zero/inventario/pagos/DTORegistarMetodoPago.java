package angel_zero.inventario.pagos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tipoPago", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DTOCuentaPayPal.class, name = "PAYPAL"),
    @JsonSubTypes.Type(value = DTODatosTarjeta.class, name = "TARJETA")
})
public class DTORegistarMetodoPago {

    @NotBlank
    private String propietarioMetodo;

    @NotBlank
    private String calleFacturacion;

    @NotBlank
    @JsonProperty("tipoPago") // Asegura que Jackson lo detecte
    private String tipoPago;

    public DTORegistarMetodoPago(String propietarioMetodo, String calleFacturacion, String tipoPago) {
        this.propietarioMetodo = propietarioMetodo;
        this.calleFacturacion = calleFacturacion;
        this.tipoPago = tipoPago;
    }
}
