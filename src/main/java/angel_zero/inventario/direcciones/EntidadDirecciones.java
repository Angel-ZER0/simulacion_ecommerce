package angel_zero.inventario.direcciones;

import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.proveedores.EntidadProveedores;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntidadDirecciones {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String calle;
	
	@Column(nullable = false, length = 255)
	private String colonia;
	
	@Pattern(regexp = "\\d{5}")
	@Column(nullable = false)
	private String codigoPostal;
	
    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false)
    private EntidadMunicipiosAlcaldias municipio;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private EntidadEstados estado;
	
	@Column(length = 255, nullable = true)
	private String referenciasOpcionales;
	
	@Column(nullable = false)
	private String latitud;
	
	@Column(nullable = false)
	private String longitud;
	
	@Column(nullable = false)
	boolean direccionPreferida;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private EntidadClientes idCliente;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private EntidadProveedores idProveedor;

	public EntidadDirecciones(String calle, String colonia, @Pattern(regexp = "\\d{5}") String codigoPostal,
			EntidadMunicipiosAlcaldias municipio, EntidadEstados estado, String referenciasOpcionales, 
			String latitud, String longitud, boolean direccionPreferida, EntidadClientes cliente) {
		this.calle = calle;
		this.colonia = colonia;
		this.codigoPostal = codigoPostal;
		this.municipio = municipio;
		this.estado = estado;
		this.referenciasOpcionales = referenciasOpcionales;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccionPreferida = direccionPreferida;
		this.idCliente = cliente;
	}
	
	public EntidadDirecciones(String calle, String colonia, @Pattern(regexp = "\\d{5}") String codigoPostal,
			EntidadMunicipiosAlcaldias municipio, EntidadEstados estado, String referenciasOpcionales, 
			String latitud, String longitud, EntidadProveedores proveedor) {
		this.calle = calle;
		this.colonia = colonia;
		this.codigoPostal = codigoPostal;
		this.municipio = municipio;
		this.estado = estado;
		this.referenciasOpcionales = referenciasOpcionales;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccionPreferida = false;
		this.idProveedor = proveedor;
	}

	public EntidadDirecciones(DTORegistrarDireccion registroDireccion, boolean direccionPreferida, EntidadMunicipiosAlcaldias demarcacion,
			EntidadEstados estado, EntidadClientes cliente) {
		this.calle = registroDireccion.calle();
		this.colonia = registroDireccion.colonia();
		this.codigoPostal = registroDireccion.codigoPostal();
		this.municipio = demarcacion;
		this.estado = estado;
		this.referenciasOpcionales = registroDireccion.referenciasOpcionales();
		this.idCliente = cliente;
		this.idProveedor = null;
		this.latitud = registroDireccion.latitud();
		this.longitud = registroDireccion.longitud();
		this.direccionPreferida = direccionPreferida;
	}
	
	public EntidadDirecciones(DTORegistrarDireccion registroDireccion, EntidadMunicipiosAlcaldias demarcacion,
			EntidadEstados estado, EntidadProveedores proveedor) {
		this.calle = registroDireccion.calle();
		this.colonia = registroDireccion.colonia();
		this.codigoPostal = registroDireccion.codigoPostal();
		this.municipio = demarcacion;
		this.estado = estado;
		this.referenciasOpcionales = registroDireccion.referenciasOpcionales();
		this.idCliente = null;
		this.idProveedor = proveedor;
		this.latitud = registroDireccion.latitud();
		this.longitud = registroDireccion.longitud();
		this.direccionPreferida = false;
	}
	
	public void actualizarDireccion(DTOEdicionDireccion edicionDireccion) {
		
		if (edicionDireccion.calle() != null) {
			
			this.calle = edicionDireccion.calle();
			
		}
		
		if (edicionDireccion.colonia() != null) {
			
			this.colonia = edicionDireccion.colonia();
			
		}
		
		if (edicionDireccion.codigoPostal() != null) {
			
			this.codigoPostal = edicionDireccion.codigoPostal();
			
		}
		
		if (edicionDireccion.referenciasOpcionales() != null) {
			
			this.referenciasOpcionales = edicionDireccion.referenciasOpcionales();
			
		}
		
	}
	
	public void actualizarMunicipio(EntidadMunicipiosAlcaldias municipio) {
		
		this.municipio = municipio;
		this.estado = municipio.getEstado();
		
	}
	
}
