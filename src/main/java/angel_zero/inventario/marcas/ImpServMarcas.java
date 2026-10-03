package angel_zero.inventario.marcas;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import angel_zero.inventario.productos.RepositorioProductos;
import angel_zero.inventario.proveedores.EntidadProveedores;
import angel_zero.inventario.rolesPermisos.ServSelecUsuario;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServMarcas implements IntServMarcas {

	private final RepositorioMarcas repoMarcas;
	private final RepositorioProductos repoProductos;
	private final ServSelecUsuario servUsuario;

	@Override
	public ResponseEntity anadirMarca(DTOMarcaAnadida nuevaMarca) { 
		
		EntidadMarcas registroMarca = repoMarcas
				.save(new EntidadMarcas(nuevaMarca, (EntidadProveedores) servUsuario.obtenerEntidadRelacionada()));

		return ResponseEntity.ok(new DTOInformacionMarca(registroMarca));
		
	}

	@Override
	public ResponseEntity actualizarMarca(Long idMarca, DTOMarcaAnadida actualizarMarca) {
		
		Optional <EntidadMarcas> marca = repoMarcas.seleccionarMarcaPorProovedor(idMarca, (EntidadProveedores) servUsuario.obtenerEntidadRelacionada());
		
		if (marca.isEmpty()) {
			
			return ResponseEntity.status(403).build();
			
		} 
			
		marca.get().actualizarMarca(actualizarMarca);
		
		return ResponseEntity.ok(new DTOInformacionMarca(marca.get()));
		
	}

	@Override
	public ResponseEntity listarMarcas(Pageable paginacion) {
	
		return ResponseEntity.ok(
				repoMarcas.marcasPorEmpresa((EntidadProveedores) servUsuario.obtenerEntidadRelacionada(), paginacion)
						.map(DTOInformacionMarca::new));
	
	}

	@Override
	public ResponseEntity eliminarMarca(Long idMarca) {
		
		EntidadProveedores proovedor = (EntidadProveedores) servUsuario.obtenerEntidadRelacionada();
		
		EntidadMarcas marca = repoMarcas.seleccionarMarcaPorProovedor(idMarca, proovedor).orElseThrow(() -> 
		new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));

		repoProductos.eliminarProductosPorMarca(marca);
		repoMarcas.eliminarMarca(idMarca, proovedor);
		return ResponseEntity.noContent().build();
		
	}

}
