package angel_zero.inventario.admins;

import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntidadAdmins {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255, unique = true)
	private String nombreUsuario;
	
	@OneToOne(mappedBy = "admin")
	private EntidadRelacionRolesUsuarios rolesAdmin;
	/*
	@Column(nullable = false, length = 255)
	private String correo;
	
	@Column(nullable = false)
	private String contrasena;
	 */
	public EntidadAdmins(DTOCrearAdmin nuevoAdmin) {
		this.nombreUsuario = nuevoAdmin.nombreUsuario();
	}
	
}
