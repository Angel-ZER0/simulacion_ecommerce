package angel_zero.inventario.ordenesProductos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productos/carrito")
public class ControladorOrdenesClientes {

	private final ImplementacionServiciosOrdenes serviciosOrdenes;
	/*
	@PostMapping
	public ResponseEntity crearNuevaOrden(@RequestBody @Valid DTOCrearOrden orden) {
		
		return serviciosOrdenes.crearOrden(orden);		
	}
	*/
	/*
	@GetMapping()
	public ResponseEntity listarProductosOrdenActiva () {
		
		
		
	}
	*/
}
