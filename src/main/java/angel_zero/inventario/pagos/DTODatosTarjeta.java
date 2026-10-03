package angel_zero.inventario.pagos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeName("TARJETA") 
public class DTODatosTarjeta extends DTORegistarMetodoPago {

    @Pattern(regexp = "^\\d{16}$")
    private String numerosTarjeta;

    @Pattern(regexp = "^\\d{2}$")
    private String mesExpiracion;

    @Pattern(regexp = "^\\d{4}$")
    private String anoExpiracion;

    @Pattern(regexp = "^\\d{3}$")
    private String cvv;

    public DTODatosTarjeta(@JsonProperty("propietarioMetodo") String propietarioMetodo,
                           @JsonProperty("calleFacturacion") String calleFacturacion,
                           @JsonProperty("tipoPago") String tipoPago,
                           @JsonProperty("numerosTarjeta") String numerosTarjeta,
                           @JsonProperty("mesExpliracion") String mesExpliracion,
                           @JsonProperty("anoExpiracion") String anoExpiracion,
                           @JsonProperty("cvv") String cvv) {
        super(propietarioMetodo, calleFacturacion, tipoPago);
        this.numerosTarjeta = numerosTarjeta;
        this.mesExpiracion = mesExpliracion;
        this.anoExpiracion = anoExpiracion;
        this.cvv = cvv;
    }
}

