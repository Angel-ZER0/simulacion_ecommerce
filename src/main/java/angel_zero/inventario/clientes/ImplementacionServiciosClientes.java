package angel_zero.inventario.clientes;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import angel_zero.inventario.configSeguridad.ServicioEncriptarContrasena;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionOrdenActiva;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionProducto404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionDireccion400;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionDireccion404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionDireccionYMetodo400;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionEnElPago;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionEstado404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionMetodo400;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionMetodo404;
import angel_zero.inventario.controladoresDeExcepciones.ExcepcionMunicipio404;
import angel_zero.inventario.direcciones.DTORegistrarDireccion;
import angel_zero.inventario.direcciones.DireccionYDistancia;
import angel_zero.inventario.direcciones.EncontrarSucursalMasCercana;
import angel_zero.inventario.direcciones.EntidadDirecciones;
import angel_zero.inventario.direcciones.EntidadEstados;
import angel_zero.inventario.direcciones.EntidadMunicipiosAlcaldias;
import angel_zero.inventario.direcciones.RepositorioDirecciones;
import angel_zero.inventario.direcciones.RepositorioEstados;
import angel_zero.inventario.direcciones.RepositorioMunicipiosAlcaldias;
import angel_zero.inventario.historialOrdenes.DTOCompraRealizada;
import angel_zero.inventario.historialOrdenes.DTOConfirmacionVaciarCarrito;
import angel_zero.inventario.historialOrdenes.DTOEstadoABuscar;
import angel_zero.inventario.historialOrdenes.DTOMontoTotalCarrito;
import angel_zero.inventario.historialOrdenes.EntidadEstadoPagoOrden;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.historialOrdenes.RepositorioEstadoEntrega;
import angel_zero.inventario.historialOrdenes.RepositorioEstadoPago;
import angel_zero.inventario.historialOrdenes.RepositorioHistorialOrdenes;
import angel_zero.inventario.ordenesProductos.DTOMostrarOrden;
import angel_zero.inventario.ordenesProductos.EntidadEnvios;
import angel_zero.inventario.ordenesProductos.EntidadOrdenesProductos;
import angel_zero.inventario.ordenesProductos.RepositorioEnvios;
import angel_zero.inventario.ordenesProductos.RepositorioOrdenesProductos;
import angel_zero.inventario.pagos.DTOCuentaPayPal;
import angel_zero.inventario.pagos.DTODatosTarjeta;
import angel_zero.inventario.pagos.DTOInfoTarjeta;
import angel_zero.inventario.pagos.DTOInformacionPayPal;
import angel_zero.inventario.pagos.DTORegistarMetodoPago;
import angel_zero.inventario.pagos.EntidadCuentaPayPal;
import angel_zero.inventario.pagos.EntidadMetodosPago;
import angel_zero.inventario.pagos.EntidadTarjetaPago;
import angel_zero.inventario.pagos.EntidadTipoTarjeta;
import angel_zero.inventario.pagos.RepositorioMetodosPago;
import angel_zero.inventario.pagos.RepositorioTiposTarjeta;
import angel_zero.inventario.pagos.SimuladorDePago;
import angel_zero.inventario.productos.RepositorioProductos;
import angel_zero.inventario.productos.ServicioActualizarInventarioAlmacen;
import angel_zero.inventario.proveedores.EntidadProveedores;
import angel_zero.inventario.proveedores.RepositorioProveedores;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import angel_zero.inventario.rolesPermisos.ServSelecUsuario;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplementacionServiciosClientes implements InterfazServiciosClientes {
	
	private final RepositorioProductos repoProductos;
	private final RepositorioClientes repoClientes;
	private final RepositorioRolesUsuarios repoRolUsu;
	private final RepositorioRoles repoRoles;
	private final RepositorioOrdenesProductos repoOrdenes;
	private final RepositorioHistorialOrdenes repoHistorial;
	private final RepositorioEstados repoEstados;
	private final RepositorioMunicipiosAlcaldias repoDemarcaciones;
	private final RepositorioDirecciones repoDirecciones;
	private final RepositorioEstadoPago repoEstadoPago;
	private final RepositorioEnvios repoEnvios;
	private final RepositorioEstadoEntrega repoEstadoEntrega;
	private final RepositorioProveedores repoProveedores;
	private final RepositorioMetodosPago repoMetodosPago;
	private final RepositorioTiposTarjeta repoTipoTarjeta;
	private final ServicioEncriptarContrasena encriptadorContrasena;
	private final ServSelecUsuario servSelecUsuario;
	private final HttpServletRequest request;
	private final ServicioActualizarInventarioAlmacen servActCantProducto;
	
	private String crearUUIDCliente() {
		
		String [] arregloLetrasNumeros = {"A", "B", "C", "D", "E", "F", 
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", 
				"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", 
				"c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", 
				"y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		SecureRandom RANDOM = new SecureRandom();
	
		StringBuilder UUID = new StringBuilder();
		
		for (int i = 0; i < 12; i++) {
            
			int caracter = RANDOM.nextInt(arregloLetrasNumeros.length);
			UUID.append(arregloLetrasNumeros[caracter]);
			
        }
        
		return UUID.toString();
		
	}
	/*
	public void verificarExpiracion(YearMonth fecha) {
		
		if (fecha.isBefore(YearMonth.now())) {
			
			throw new IllegalArgumentException("La tarjeta ha expirado");
			
		}
		
	}
	
	public void verificarFecha(String mes, String ano) {
		
		if (Integer.valueOf(mes) <= 0 || Integer.valueOf(mes) > 12) {
			
			throw new IllegalArgumentException("Mes no válido");
			
		}
		
		if (Integer.valueOf(ano) > Integer.valueOf(Year.now().toString()) + 8) {
			
			throw new IllegalArgumentException("Año no válido");
		}
		
	}
	
	public YearMonth datosAYearMonth(String mes, String ano) {
		
		YearMonth fechaExpiración = YearMonth.of(Integer.valueOf(mes), Integer.valueOf(ano));
		
		return fechaExpiración;
		
	}
	*/
	
	public YearMonth validarYVerificarFecha(String mes, String ano) {
		
	    try {
	        int mesInt = Integer.parseInt(mes);
	        int anoInt = Integer.parseInt(ano);

	        // Validar mes
	        if (mesInt < 1 || mesInt > 12) {
	            throw new IllegalArgumentException("Mes no válido: " + mes);
	        }

	        // Validar año
	        int currentYear = Year.now().getValue();
	        if (anoInt < currentYear || anoInt > currentYear + 8) {
	            throw new IllegalArgumentException("Año no válido: " + ano);
	        }

	        // Crear YearMonth y verificar expiración
	        YearMonth fechaExpiracion = YearMonth.of(anoInt, mesInt);
	        if (fechaExpiracion.isBefore(YearMonth.now())) {
	            throw new IllegalArgumentException("La tarjeta ha expirado");
	        }

	        return fechaExpiracion;

	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Mes o año no son números válidos", e);
	    }
	    
	}
	
	@Override
	public ResponseEntity registrarNuevoCliente(DTORegistroNuevoCliente nuevoCliente) {

		if (repoClientes.existsByTelefono(nuevoCliente.telefono())) {

			return ResponseEntity.status(409).body("Este número de télefono ya ha sido registrado");

		} else {

			EntidadRoles rolCliente = repoRoles.findByNombreRol("cliente");

			List<EntidadRoles> roles = new ArrayList<EntidadRoles>();

			roles.add(rolCliente);

			System.out.println(rolCliente);

			// registroNuevoCliente.setContrasena(passwordEncoder.encode(registroNuevoCliente.getContrasena()));
			// *registroNuevoCliente.setUUID("huevos123456");
			/*
			 * while(repoClientes.existsByUUID(crearUUIDCliente())) {
			 * 
			 * registroNuevoCliente.setUUID(crearUUIDCliente());
			 * 
			 * }
			 */

			EntidadClientes registroNuevoCliente = repoClientes.save(new EntidadClientes(nuevoCliente));

			EntidadRelacionRolesUsuarios asignacionRoles = repoRolUsu.save(
					new EntidadRelacionRolesUsuarios(registroNuevoCliente, roles, nuevoCliente.credenciales().correo(),
							encriptadorContrasena.contrasenaEncriptada(nuevoCliente.credenciales().contrasena())));

			return ResponseEntity.ok(new DTOInformacionCliente(registroNuevoCliente));

		}

	}
	/*
	@Override
	public ResponseEntity accesoCliente(DTOCredenciales credencialesClientes) {
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(credencialesClientes.correo(), 
				credencialesClientes.contrasena());
		var autenticarUsuario = autenticacion.authenticate(authToken);
		String JWTToken = servicioToken.generacionToken((EntidadRelacionRolesUsuarios) autenticarUsuario.getPrincipal());
		return ResponseEntity.ok(new TokenGenerado(JWTToken));
		
	}
	*/
	@Override
	public ResponseEntity listarClientes(Pageable paginacion) {
		
		return ResponseEntity.ok(repoClientes.findAll(paginacion).map(DTOInformacionCliente::new));
	
	}
	
	@Override
	public ResponseEntity<Page<DTOMostrarOrden>> comprasEnCarrito(Pageable paginacion) {
	
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		return ResponseEntity.ok(repoOrdenes.listaDeProductosEnOrden(cliente, paginacion)
				.map(DTOMostrarOrden::new));
		
	}
	
	@Override
	public ResponseEntity <DTOMontoTotalCarrito> montoDelCarrito() {
		
		EntidadHistorialOrdenes ordenActual = repoHistorial.localizarOrdenActiva((EntidadClientes) servSelecUsuario
				.obtenerEntidadRelacionada()).orElseThrow(ExcepcionOrdenActiva::new);
		return ResponseEntity.ok(new DTOMontoTotalCarrito(ordenActual.getTotalAPagar()));
		
	}

	@Override
	public ResponseEntity<String> eliminarProductoDeOrden(Long idProducto) {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		EntidadOrdenesProductos productoEnOrden = repoOrdenes.productoAEliminar(idProducto, cliente)
				.orElseThrow(ExcepcionProducto404::new);
		repoOrdenes.deleteById(productoEnOrden.getId());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Producto eliminado correctamente.");
		
	}

	@Override
	public ResponseEntity eliminarOrdenCompleta(DTOConfirmacionVaciarCarrito confirmacion) {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		EntidadHistorialOrdenes ordenActiva = repoHistorial.localizarOrdenActiva(cliente)
				.orElseThrow(ExcepcionOrdenActiva::new);
		
		if (confirmacion.confirmacion() == true) {
			
			repoOrdenes.eliminarOrdenCompleta(ordenActiva, cliente);
			return ResponseEntity.noContent().build();
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se eliminó nimgún producto del carrito.");
	}

	
	@Override
	public ResponseEntity<Page<DTOCompraRealizada>> comprasRealizadas(Pageable paginacion, DTOEstadoABuscar estado) {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		
		EntidadEstadoPagoOrden estadoBuscado;
		
		if (estado.estado() == null || estado.estado() != "") {
			
			//estadoBuscado = repoEstadoPago.findByEstado("finalizado");
			
			return ResponseEntity.ok(repoHistorial.encontrarOrdenesCliente(paginacion, cliente).map((DTOCompraRealizada::new)));
			
		} else {
			
			estadoBuscado = repoEstadoPago.findByEstado(estado.estado());
			
			if (estadoBuscado == null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				
			}
			
		}
		
		return ResponseEntity.ok(repoHistorial.productosEnOrdenesAnteriores(cliente, estadoBuscado, paginacion).map(DTOCompraRealizada::new));
		
	}

	@Override
	public ResponseEntity registrarMetodoPago(DTORegistarMetodoPago registrarMetodoPago) {
		
		EntidadClientes idCliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		
		boolean metodoPreferido = !repoMetodosPago.existsByIdCliente(idCliente);
		
		if ("TARJETA".equals(registrarMetodoPago.getTipoPago())) {
			
			if (!(registrarMetodoPago instanceof DTODatosTarjeta)) {
				
				throw new IllegalArgumentException("Los datos de la tarjeta no son válidos.");
				
			}
			
			DTODatosTarjeta datosTarjeta = (DTODatosTarjeta) registrarMetodoPago;
			
			int numeroAleatorio = (int) (Math.random() * 2) + 1;
			
			EntidadTipoTarjeta tipoTarjeta = repoTipoTarjeta.findById(Long.valueOf(numeroAleatorio)).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
			
			BigDecimal saldoDisponible = tipoTarjeta.getId() == 1 
				    ? BigDecimal.valueOf(100_000) 
				    : BigDecimal.valueOf(500);
			
			YearMonth fechaExpiracion = validarYVerificarFecha(datosTarjeta.getMesExpiracion(), datosTarjeta.getAnoExpiracion());
			
			EntidadTarjetaPago registrarTarjeta = repoMetodosPago.save(new EntidadTarjetaPago(datosTarjeta, metodoPreferido, idCliente,
					fechaExpiracion, tipoTarjeta, saldoDisponible));
			
			DTOInfoTarjeta infoTarjeta = new DTOInfoTarjeta(registrarTarjeta);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(infoTarjeta);
			
		} else if ("PAYPAL".equals(registrarMetodoPago.getTipoPago())) {
			
			if(!(registrarMetodoPago instanceof DTOCuentaPayPal)) {
				
				throw new IllegalArgumentException("Los datos de la cuenta no son válidos-");
				
			}
			
			DTOCuentaPayPal datosPayPal = (DTOCuentaPayPal) registrarMetodoPago;
			
			EntidadCuentaPayPal cuentaPayPal = repoMetodosPago.save(new EntidadCuentaPayPal(datosPayPal, metodoPreferido, idCliente));
			
			DTOInformacionPayPal infoCuenta = new DTOInformacionPayPal(cuentaPayPal);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(infoCuenta);
			
		} else {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verifica los datos del método que se intenta registrar.");
			
		}
		
	}
	
/*	@Override
	public ResponseEntity<?> registrarMetodoPago(DTORegistarMetodoPago registrarMetodoPago) {
	    try {
	        // 1. Obtener cliente autenticado
	        EntidadClientes idCliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
	        
	        // 2. Validar tipo de pago
	        String tipoPago = registrarMetodoPago.getTipoPago();
	        if (tipoPago == null) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de pago es requerido");
	        }

	        // 3. Procesar según el tipo de pago
	        EntidadMetodosPago metodoPago;
	        switch (tipoPago) {
	            case "TARJETA":
	                metodoPago = registrarTarjeta((DTODatosTarjeta) registrarMetodoPago, idCliente);
	                break;
	            case "PAYPAL":
	                metodoPago = registrarPayPal((DTOCuentaPayPal) registrarMetodoPago, idCliente);
	                break;
	            default:
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Método de pago no soportado: " + tipoPago);
	        }

	        return ResponseEntity.status(HttpStatus.CREATED).body(metodoPago);

	    } catch (ClassCastException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estructura de datos incorrecta para el tipo de pago", e);
	    } catch (DataAccessException e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al acceder a la base de datos", e);
	    } catch (IllegalArgumentException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al registrar el método de pago", e);
	    }
	}
*/
	/*
	private EntidadTarjetaPago registrarTarjeta(DTODatosTarjeta datosTarjeta, EntidadClientes idCliente) {
	    try {
	        // Validar y obtener tipo de tarjeta aleatorio
	        EntidadTipoTarjeta tipoTarjeta = repoTipoTarjeta.findById((long) ((Math.random() * 2) + 1))
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de tarjeta no encontrado"));

	        // Validar fecha de expiración
	        YearMonth fechaExpiracion = validarYVerificarFecha(datosTarjeta.getMesExpliracion(), datosTarjeta.getAnoExpiracion());
	        
	        BigDecimal saldoDisponible = tipoTarjeta.getId() == 1 
	        	    ? BigDecimal.valueOf(100_000) 
	        	    : BigDecimal.valueOf(500);

	        // Registrar tarjeta
	        return repoMetodosPago.save(new EntidadTarjetaPago(datosTarjeta, idCliente, fechaExpiracion, tipoTarjeta, saldoDisponible));

	    } catch (DateTimeParseException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fecha de expiración inválida", e);
	    }
	}

	private EntidadCuentaPayPal registrarPayPal(DTOCuentaPayPal datosPayPal, EntidadClientes idCliente) {
	    try {
	        return repoMetodosPago.save(new EntidadCuentaPayPal(datosPayPal, idCliente));
	    } catch (DataIntegrityViolationException e) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "La cuenta PayPal ya está registrada", e);
	    }
	}
	*/
	
	public ResponseEntity<Page<DTOMostrarOrden>> productosEnOrdenTerminada(Pageable paginacion, Long idOrden) {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		return ResponseEntity.ok(repoOrdenes.listaDeProductosEnOrdenTerminada(cliente, idOrden, paginacion)
				.map(DTOMostrarOrden::new));
		
	}	
	
	@Override
	public ResponseEntity editarInformacionCliente(DTOEditarCliente cliente) {
		
		EntidadClientes actualizarCliente = repoClientes.getReferenceById(cliente.id());
		actualizarCliente.actualizarCliente(cliente);
		return ResponseEntity.ok(new DTOInformacionCliente(actualizarCliente));
	}

	@Override
	public ResponseEntity eliminarCliente(Long id) {
		
		repoClientes.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}

	@Override
	public ResponseEntity realizarCompra(Long idDireccion) {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		EntidadDirecciones direccionCliente = repoDirecciones.findByIdAndIdCliente(idDireccion, cliente)
				.orElseThrow(ExcepcionDireccion404::new);
		EntidadHistorialOrdenes ordenActiva = repoHistorial.localizarOrdenActiva(cliente)
				.orElseThrow(ExcepcionOrdenActiva::new);
		Long estadoPago = 0L;
			
			try {
				estadoPago = SimuladorDePago.estadoPago();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (estadoPago == 5) {
				
				//ignorado
				/*
				ordenActiva.setEstadoTransaccion(repoEstadoPago.findById(5L).get());
				
				List <EntidadProveedores> listaProveedoresEnOrden = new ArrayList<>();
				
				ordenActiva.getNumeroDeOrden().forEach(p -> listaProveedoresEnOrden.add(
						p.getProductoSolicitado().getMarca().getProovedorMarca()));
				
				List <EntidadProveedores> listaFiltrada = new ArrayList<>(); 
						
				listaProveedoresEnOrden.stream().distinct().forEach(pr -> listaFiltrada.add(pr));
				
				List <EntidadDirecciones> listaDireccionesSucursales = new ArrayList<EntidadDirecciones>();
						
				for (int i = 0; i < listaFiltrada.size(); i++) {
					
					String [] idSucursalMenor = 
					EncontrarSucursalMasCercana.arregloDistanciaMasCorta(
							EncontrarSucursalMasCercana.distanciaClienteSucursales(direccionCliente, listaFiltrada.get(i)
									.getDirecciones()));
					
					EntidadDirecciones direccionSucursal = repoDirecciones.findById(Long.valueOf(idSucursalMenor[0])).get();
					
					listaDireccionesSucursales.add(direccionSucursal);
					
				}
			
				EntidadEnvios nuevoEnvio = repoEnvios.save(new EntidadEnvios(direccionCliente, listaDireccionesSucursales,
						repoEstadoEntrega.findById(1L).get()));
				
				listaDireccionesSucursales.forEach(ds -> System.out.println("id direccion sucursal: " + ds.getId()));
				
				ordenActiva.setEstadoPaquete(repoEstadoEntrega.findById(1L).get());
				ordenActiva.setActiva(false);
				
				*/
				//hasta aquí
				List <EntidadProveedores> provedoresEnLaOrden = repoProveedores.encontrarProveedoresPorOrden(ordenActiva);
				
				List <EntidadDirecciones> listaDireccionesSucursales = new ArrayList<EntidadDirecciones>();
				
				for (int i = 0; i < provedoresEnLaOrden.size(); i++) {
					
					DireccionYDistancia direccionSucursal = 
					EncontrarSucursalMasCercana.arregloDistanciaMasCorta(
							EncontrarSucursalMasCercana.distanciaClienteSucursales(direccionCliente, provedoresEnLaOrden.get(i)
									.getDirecciones()));
					
					listaDireccionesSucursales.add(direccionSucursal.direccion());
					
				}
				
				EntidadEnvios nuevoEnvio = repoEnvios.save(new EntidadEnvios(direccionCliente, listaDireccionesSucursales,
						repoEstadoEntrega.findById(1L).get()));
				
				listaDireccionesSucursales.forEach(ds -> System.out.println("id direccion sucursal: " + ds.getId()));
				ordenActiva.setActiva(false);
				
				return ResponseEntity.ok("tus productos están en camino"); 
				
			} else {
				
				return ResponseEntity.ok(estadoPago.toString());
				
			}
		
		//return ResponseEntity.ok("Algo malió sal " + estadoPago.toString());
		
	}
	
	@Override
	public ResponseEntity cancelarOrden(Long idHistorialOrdenes) {
		
		
		return null;
		
	}
	
	@Override
	public ResponseEntity verificarRequisitos() {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		
		boolean existeDireccion = repoDirecciones.existsByIdCliente(cliente);
		boolean existeMetodoPago = repoMetodosPago.existsByIdCliente(cliente);
		
		if (existeDireccion && existeMetodoPago) {
			
			return ResponseEntity.ok().body(Map.of("redirectUrl", "/clientes/finalizar-compra"));
			
		} else if (!existeDireccion && !existeMetodoPago) {
			
			throw new ExcepcionDireccionYMetodo400();
			
		} else {
			
			if (!existeDireccion) {
				
				throw new ExcepcionDireccion400();
				
			}
			
			throw new ExcepcionMetodo400();
			
		}
		
	}

	@Override
	public ResponseEntity realizarCompraSeria() {
		
		EntidadClientes cliente = (EntidadClientes) servSelecUsuario.obtenerEntidadRelacionada();
		
		EntidadHistorialOrdenes ordenActiva = repoHistorial.localizarOrdenActiva(cliente)
				.orElseThrow(ExcepcionOrdenActiva::new);
		
		EntidadDirecciones direccionCliente = repoDirecciones.encontrarDireccionPreferidaCliente(cliente).orElseThrow(
				ExcepcionDireccion404::new);
		
		EntidadMetodosPago metodoCliente = repoMetodosPago.encontrarMetodoPreferidoCliente(cliente).orElseThrow(
				ExcepcionMetodo404::new);
		
		List <EntidadProveedores> provedoresEnLaOrden = repoProveedores.encontrarProveedoresPorOrden(ordenActiva);

		List <EntidadDirecciones> listaSucursales = new ArrayList<>();
		
		for (int i = 0; i < provedoresEnLaOrden.size(); i++) {
			
			DireccionYDistancia direccionSucursal = 
			EncontrarSucursalMasCercana.arregloDistanciaMasCorta(
					EncontrarSucursalMasCercana.distanciaClienteSucursales(direccionCliente, provedoresEnLaOrden.get(i)
							.getDirecciones()));
			
			listaSucursales.add(direccionSucursal.direccion());
			
		}

		if ("PAYPAL".equals(metodoCliente.getTipoPago())) {
			
			EntidadEstadoPagoOrden estadoPago = repoEstadoPago.findById(5L).get();
			
			ordenActiva.setEstadoTransaccion(estadoPago);
			servActCantProducto.actualizarInventario(ordenActiva);
			ordenActiva.actualizarEstadoOrden(estadoPago);
			EntidadEnvios nuevoEnvio = repoEnvios.save(new EntidadEnvios(direccionCliente, listaSucursales,
					repoEstadoEntrega.findById(1L).get()));
			ordenActiva.actualizarEstadoEntrega(nuevoEnvio);
			
			
			
			
			/*return ResponseEntity.ok().body("Felicidades Shinji, tú método de pago por defecto es: " + metodoCliente.toString()
			+ "y tú direccion es: " + direccionCliente.toString());*/
			
			return ResponseEntity.ok(cliente.getNombreCliente() + " tus productos están " + nuevoEnvio.getEstadoPaquete().getEstado());

		} else if ("TARJETA".equals(metodoCliente.getTipoPago())) {
			
			EntidadTarjetaPago saldoTarjeta = (EntidadTarjetaPago) metodoCliente;
			
			if (saldoTarjeta.getSaldoDisponible().compareTo(ordenActiva.getTotalAPagar()) < 0) {
				
				ordenActiva.actualizarEstadoOrden(repoEstadoPago.getReferenceById(3L));
				ordenActiva.setEstadoPaquete(repoEstadoEntrega.getReferenceById(6L));
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Ocurrió un error con el pago, faltan fondos en el monedero para realizar la trasancción");
				
			}
			
			saldoTarjeta.actualizarSaldo(ordenActiva.getTotalAPagar());
			
			EntidadEstadoPagoOrden estadoPago = repoEstadoPago.findById(5L).get();
			
			servActCantProducto.actualizarInventario(ordenActiva);
			ordenActiva.actualizarEstadoOrden(estadoPago);
			EntidadEnvios nuevoEnvio = repoEnvios.save(new EntidadEnvios(direccionCliente, listaSucursales,
					repoEstadoEntrega.findById(1L).get()));
			ordenActiva.actualizarEstadoEntrega(nuevoEnvio);
			
			return ResponseEntity.ok(cliente.getNombreCliente() + " tus productos están " + nuevoEnvio.getEstadoPaquete().getEstado());
			
		} else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo salió mal con la petición");
			
		}
		
	}

}
