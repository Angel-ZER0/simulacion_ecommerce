package angel_zero.inventario.rolesPermisos;

import java.util.List;

import angel_zero.inventario.admins.EntidadAdmins;
import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.proveedores.EntidadProveedores;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String nombreRol;
	
	@ManyToMany(mappedBy = "rolesUsuario")
	private List <EntidadRelacionRolesUsuarios> usuarios;
	
	public EntidadRoles(String rol) {
		this.nombreRol = rol;
	}
	
}
