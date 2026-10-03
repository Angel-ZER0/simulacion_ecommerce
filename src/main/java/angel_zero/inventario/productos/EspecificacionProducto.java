package angel_zero.inventario.productos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EspecificacionProducto {
	
	public static Specification<EntidadProductos> criteriosDeFiltrado (
			String nombre,
			String marca,
			String empresa,
			String categoria
	) {
		
		return (Root<EntidadProductos> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			
			List <Predicate> predicados = new ArrayList<>();
			
			if (nombre != null && !nombre.isBlank()) {
				
				predicados.add(cb.like(cb.lower(root.get("nombreProducto")), "%" + nombre.toLowerCase() + "%"));
				
			}
			
			if (marca != null && !marca.isBlank()) {
				
				predicados.add(cb.like(cb.lower(root.get("marca").get("marca")), "%" + marca.toLowerCase() + "%"));
				
			}
			
			if (empresa != null && !empresa.isBlank()) {
				
				predicados.add(cb.like(cb.lower(root.get("marca").get("proovedorMarca").get("nombreEmpresa")), "%" + empresa.toLowerCase() + "%"));
				
			}
			
			if (categoria != null && !categoria.isBlank()) {
				
				predicados.add(cb.like(cb.lower(root.get("categoria").get("categoria")), "%" + categoria.toLowerCase() + "%"));
				
			}
			
			return predicados.isEmpty() ? cb.conjunction() : cb.and(predicados.toArray(new Predicate[0]));
			
		};
		
	}

}
