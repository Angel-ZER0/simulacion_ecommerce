package angel_zero.inventario.rolesPermisos;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import angel_zero.inventario.admins.EntidadAdmins;
import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.proveedores.EntidadProveedores;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios_roles")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class EntidadRelacionRolesUsuarios implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "id_cliente", nullable = true)
	private EntidadClientes cliente;
	
	@OneToOne
	@JoinColumn(name = "id_proovedor", nullable = true)
	private EntidadProveedores proovedor;
	
	@OneToOne
	@JoinColumn(name = "id_admin", nullable = true)
	private EntidadAdmins admin;
	
	@Column(length = 255, nullable = false, unique = true)
	private String correo;
	
	@Column(length = 512, nullable = false)
	private String contrasena;
	
	@ManyToMany//(fetch =FetchType.EAGER)
	@JoinTable(
			name = "asignacion_roles_usuarios",
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id", nullable = false)
			)
	private List <EntidadRoles> rolesUsuario;

	public EntidadRelacionRolesUsuarios(EntidadClientes cliente, List <EntidadRoles> roles, String correo, String contrasena) {
		
		this.cliente = cliente;
		this.proovedor = null;
		this.admin = null;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rolesUsuario = roles;
		
	}

	public EntidadRelacionRolesUsuarios(EntidadProveedores proovedores, List <EntidadRoles> roles, String correo, String contrasena) {
	
		this.proovedor = proovedores;
		this.admin = null;
		this.cliente = null;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rolesUsuario = roles;
		
	}

	public EntidadRelacionRolesUsuarios(EntidadAdmins admins, List<EntidadRoles> rolesUsuario, String correo, String contrasena) {

		this.admin = admins;
		this.cliente = null;
		this.proovedor = null;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rolesUsuario = rolesUsuario;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return rolesUsuario.stream().map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombreRol())).toList();
		
	}

	@Override
	public String getPassword() {
		
		return contrasena;
		
	}

	@Override
	public String getUsername() {
		
		return correo;
		
	}

}
