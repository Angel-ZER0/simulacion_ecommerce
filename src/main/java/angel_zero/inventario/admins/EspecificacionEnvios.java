package angel_zero.inventario.admins;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import angel_zero.inventario.ordenesProductos.EntidadEnvios;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EspecificacionEnvios {

	public static Specification<EntidadEnvios> criteriosFiltrado(
			
			LocalDate fecha,
			String estadoEntrega
			
			) {
		
		return (Root<EntidadEnvios> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			
			List<Predicate> predicado = new ArrayList<>();
			
			if (fecha != null) {
				
				LocalDateTime inicioDia = fecha.atStartOfDay();
				LocalDateTime finalDia = fecha.plusDays(1).atStartOfDay().minusNanos(1);
				
				Predicate fechaEntrega = cb.between(root.get("fechaEntrega"), inicioDia, finalDia);
				Predicate fechaEstimadaEntrega = cb.between(root.get("fechaEstimadaDeEntrega"), inicioDia, finalDia);
				
				predicado.add(cb.or(fechaEntrega, fechaEstimadaEntrega));
				
			}
			
			if(estadoEntrega != null && !estadoEntrega.trim().isEmpty()) {
				
				predicado.add(cb.like(cb.lower(root.get("estadoPaquete").get("estado")), "%" + estadoEntrega.toLowerCase() + "%"));
				
			}
			
			return predicado.isEmpty() ? cb.conjunction() : cb.and(predicado.toArray(new Predicate[0]));
			
		};
		
	}
	
}
