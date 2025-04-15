package angel_zero.inventario.marcas;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import angel_zero.inventario.productos.FiltroBusquedaProductos;
import angel_zero.inventario.productos.ImpServProductos;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos/marcas")
@RequiredArgsConstructor
public class ControladorMarcas {
	
	private final ImpServMarcas servicioMarcas;
	private final ImpServProductos servProductos;

	/*
	@PostMapping
	public ResponseEntity anadirMarca(@RequestBody @Valid DTOMarcaAnadida marcaNueva) {
		
		return servicioMarcas.anadirMarca(marcaNueva);
		
	}
	
	@Transactional
	@PutMapping("/{idMarca}")
	public ResponseEntity actualizarMarca(@PathVariable Long idMarca, @RequestBody @Valid DTOMarcaAnadida marcaNueva) {
		
		return servicioMarcas.actualizarMarca(idMarca, marcaNueva);
		
	}
	
	@GetMapping()
	public ResponseEntity listarMarcas(Pageable paginacion) {
		
		return servicioMarcas.listarMarcas(paginacion);
		
	}
	
	@Transactional
	@DeleteMapping("/{idMarca}")
	public ResponseEntity eliminarMarca (@PathVariable Long idMarca) {
		
		return servicioMarcas.eliminarMarca(idMarca);
		
	}
	*/
	/*
	@Transactional
	@PutMapping("/{idMarcaProovedor}")
	public ResponseEntity medirVelocidad(@PathVariable Long idMarcaProovedor, @RequestBody @Valid DTOMarcaAnadida actualizarMarca) {
		
		Optional <EntidadMarcas> marcaProovedor = repoMarcas.findById(idMarcaProovedor);
		EntidadProovedores proovedor = (EntidadProovedores) buscarUsuario.obtenerEntidadRelacionada();
		if (marcaProovedor.isPresent() && proovedor.getMarcaEmpresa().contains(marcaProovedor.get())) {
			
			marcaProovedor.get().actualizarMarca(actualizarMarca);
			return ResponseEntity.ok(new DTOInformacionMarca(marcaProovedor.get()));
			
		} else {
			
			return ResponseEntity.badRequest().build();
		}
		
	}
	*/
}
