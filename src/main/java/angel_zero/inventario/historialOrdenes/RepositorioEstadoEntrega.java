package angel_zero.inventario.historialOrdenes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEstadoEntrega extends JpaRepository <EntidadEstadoEntregaOrden, Long> {

}
