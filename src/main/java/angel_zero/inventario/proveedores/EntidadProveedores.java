package angel_zero.inventario.proveedores;

import java.util.List;

import angel_zero.inventario.direcciones.EntidadDirecciones;
import angel_zero.inventario.marcas.EntidadMarcas;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "proovedores")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EntidadProveedores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255, unique = true)
	private String nombreEmpresa;
	
	@OneToOne(mappedBy = "proovedor")
	private EntidadRelacionRolesUsuarios rolesProovedor;
	
	@OneToMany(mappedBy = "proovedorMarca", cascade = CascadeType.ALL)
	private List <EntidadMarcas> marcasDeLaEmpresa;

	@Column(nullable = false, length = 20)
	private String telefono;
	
	@OneToMany(mappedBy = "idProveedor")
	private List <EntidadDirecciones> direcciones;
	
	public EntidadProveedores(@Valid DTORegistrarProveedor registroProovedor) {
		
		this.nombreEmpresa = registroProovedor.nombreEmpresa();
		this.telefono = registroProovedor.telefono();
		
	}

	public EntidadProveedores(String nombreEmpresa, String telefono) {
		this.nombreEmpresa = nombreEmpresa;
		this.telefono = telefono;
	}
	
	
	
}
