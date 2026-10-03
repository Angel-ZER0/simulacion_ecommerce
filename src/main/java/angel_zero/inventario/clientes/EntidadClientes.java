package angel_zero.inventario.clientes;

import java.util.ArrayList;
import java.util.List;

import angel_zero.inventario.direcciones.EntidadDirecciones;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.pagos.EntidadMetodosPago;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class EntidadClientes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/*
	@Column(nullable = false, unique = true, length = 12)
	private String UUID;
	*/
	@Column(nullable = false, length = 255)
	private String nombreCliente;
	
	@Column(nullable = false, length = 255)
	private String apellidoPaternoCliente;
	
	@Column(length = 255)
	private String apellidoMaternoCliente;

	@OneToOne(mappedBy = "cliente")
	private EntidadRelacionRolesUsuarios rolesCliente;
	/*
	@Column(nullable = false, length = 255)
	private String contrasena;

	@Email
	@Column(nullable = false, length = 255)
	private String correo;
	*/
	@Column(nullable = false, length = 20, unique = true)
	private String telefono;
	
	@OneToMany(mappedBy = "ordenCliente", cascade = CascadeType.PERSIST)
	private List <EntidadHistorialOrdenes> historialPedidos;
	
	@OneToMany(mappedBy = "idCliente")
	private List <EntidadDirecciones> direcciones;
	
	@OneToMany(mappedBy = "idCliente", cascade = CascadeType.ALL)
	private List<EntidadMetodosPago> metodosDePago = new ArrayList<>();
	
	public EntidadClientes(DTORegistroNuevoCliente nuevoCliente) {
		this.nombreCliente = nuevoCliente.nombreCliente();
		this.apellidoPaternoCliente = nuevoCliente.apellidoPaternoCliente();
		this.apellidoMaternoCliente = nuevoCliente.apellidoMaternoCliente();
		//this.contrasena = nuevoCliente.contrasena();
		this.telefono = nuevoCliente.telefono();
		//this.correo = nuevoCliente.correo();
	}
	
	public void actualizarCliente (DTOEditarCliente cliente) {
		
		if (cliente.nombreCliente() != null) {
			
			this.nombreCliente = cliente.nombreCliente();
			
		}
		
		if (cliente.apellidoPaternoClientes() != null) {
			
			this.nombreCliente = cliente.apellidoPaternoClientes();
			
		}
		
		if (cliente.apellidoMaternoCliente() != null) {
			
			this.nombreCliente = cliente.apellidoMaternoCliente();
			
		}
		/*
		if (cliente.contrasena() != null) {
			
			this.contrasena = cliente.contrasena();
			
		}
		
		if (cliente.correo() != null) {
			
			this.correo = cliente.correo();
			
		}
		*/
		if (cliente.telefono() != null) {
			
			this.telefono = cliente.telefono();
			
		}
		
	}

	public EntidadClientes(String nombreCliente, String apellidoPaternoCliente, String apellidoMaternoCliente,
			String telefono) {
		this.nombreCliente = nombreCliente;
		this.apellidoPaternoCliente = apellidoPaternoCliente;
		this.apellidoMaternoCliente = apellidoMaternoCliente;
		this.telefono = telefono;
	}
	
	
	
}
