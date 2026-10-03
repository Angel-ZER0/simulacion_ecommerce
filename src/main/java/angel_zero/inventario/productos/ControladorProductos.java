package angel_zero.inventario.productos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import angel_zero.inventario.categoria.RepositorioCategorias;
import angel_zero.inventario.marcas.RepositorioMarcas;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productos")
public class ControladorProductos {
	
	private final ImpServProductos servProductos;

	@GetMapping
	public ResponseEntity <Page<DTOInformacionProducto>> listarProductosEnExistencia(Pageable paginacion) {
		
		return servProductos.listarProductosEnVenta(paginacion);
		
	}
	
	@Transactional
	@PostMapping("/{idProducto}")
	public ResponseEntity anadirProductoACarrito (@PathVariable Long idProducto, @RequestBody @Valid DTODetalleCompra detalleCompra) {
	
		return servProductos.agregarAListaDeCompras(idProducto, detalleCompra);
		
	}
	
	@PostMapping("/buscar")
	public ResponseEntity <Page<DTOInformacionProducto>> busquedaAvanzada(@RequestBody FiltroBusquedaProductos filtro,
			Pageable paginacion) {

		return servProductos.busquedaAvanzada(filtro, paginacion);
		
	}
	
}
