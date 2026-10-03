package angel_zero.inventario.controladoresDeExcepciones;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ControladorExcepciones {
	
	@ExceptionHandler(ExcepcionUsuario404.class)
	public ResponseEntity<Map<String, Object>> manejarProductoNoEncontrado(ExcepcionUsuario404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "No hay usuario registrado con ese correo.");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}

	@ExceptionHandler(ExcepcionProducto404.class)
	public ResponseEntity<Map<String, Object>> manejarProductoNoEncontrado(ExcepcionProducto404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "producto no encontrado.");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionExistenciasDeProducto.class)
	public ResponseEntity<Map<String, Object>> manejarProductoNoEncontrado(ExcepcionExistenciasDeProducto ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", ex.getMessage());
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionMarca404.class)
	public ResponseEntity<Map<String, Object>> manejarMarcaNoEncontrada(ExcepcionMarca404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "marca no encontrada.");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionCategoria404.class)
	public ResponseEntity<Map<String, Object>> manejarCategoriaNoEncontrada(ExcepcionCategoria404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "categoría no encontrada.");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionOrdenActiva.class)
	public ResponseEntity <Map<String, Object>> manejarOrdenActivaInexistente(ExcepcionOrdenActiva ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "no hay productos en tu carrito");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionEstado404.class)
	public ResponseEntity <Map<String, Object>> manejarOrdenActivaInexistente(ExcepcionEstado404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("buscado", ex.getMessage());
		respuesta.put("error", "no es encontró ningún estado con ese nombre}");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionMunicipio404.class)
	public ResponseEntity <Map<String, Object>> manejarOrdenActivaInexistente(ExcepcionMunicipio404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("buscado", ex.getMessage());
		respuesta.put("error", "no es encontró ningún municipio con ese nombre");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionDireccionYMetodo400.class)
	public ResponseEntity <Map<String, Object>> menejarFaltaRegistros(ExcepcionDireccionYMetodo400 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "no cuenta con dirección ni método de pago registrados, debes tener minímo una direccion y "
				+ "un método de pago registrados para poder continuar");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionDireccion400.class)
	public ResponseEntity <Map<String, Object>> manejarDireccionNoExistente(ExcepcionDireccion400 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "no existe direccion registrada, debes registrar una para poder continuar");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionDireccion404.class)
	public ResponseEntity <Map<String, Object>> manejarDireccionNoEncontrada(ExcepcionDireccion404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", ex.getMessage());
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionMetodo400.class)
	public ResponseEntity <Map<String, Object>> menejarDireccionNoEncontrada(ExcepcionMetodo400 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "no existe método de pago registrado, debes registrar uno para continuar.");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionMetodo404.class)
	public ResponseEntity <Map<String, Object>> menejarDireccionNoEncontrada(ExcepcionMetodo404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "no se encontró método referido");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionEnElPago.class)
	public ResponseEntity <Map<String, Object>> menejarFalloTransaccion(ExcepcionEnElPago ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", ex.getMessage());
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionEnvio404.class)
	public ResponseEntity <Map<String, Object>> menejarEnvioNoEncontrado(ExcepcionEnvio404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", ex.getMessage());
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		
	}
	
	@ExceptionHandler(ExcepcionEstadoEntrega404.class)
	public ResponseEntity <Map<String, Object>> manejarEstadoEntregaNoEncontrado(ExcepcionEstadoEntrega404 ex) {
		
		Map <String, Object> respuesta = new HashMap<>();
		respuesta.put("error", "estado para la entrega no encontrado");
		respuesta.put("timestamp", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		
	}
	
}
