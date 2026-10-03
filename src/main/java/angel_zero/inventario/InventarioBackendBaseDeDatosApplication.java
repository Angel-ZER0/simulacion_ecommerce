package angel_zero.inventario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import angel_zero.inventario.admins.EntidadAdmins;
import angel_zero.inventario.admins.RepositorioAdmins;
import angel_zero.inventario.categoria.EntidadCategoria;
import angel_zero.inventario.categoria.RepositorioCategorias;
import angel_zero.inventario.clientes.DTOCredenciales;
import angel_zero.inventario.clientes.DTORegistroNuevoCliente;
import angel_zero.inventario.clientes.EntidadClientes;
import angel_zero.inventario.clientes.RepositorioClientes;
import angel_zero.inventario.direcciones.EntidadDirecciones;
import angel_zero.inventario.direcciones.EntidadEstados;
import angel_zero.inventario.direcciones.EntidadMunicipiosAlcaldias;
import angel_zero.inventario.direcciones.RepositorioDirecciones;
import angel_zero.inventario.direcciones.RepositorioEstados;
import angel_zero.inventario.direcciones.RepositorioMunicipiosAlcaldias;
import angel_zero.inventario.historialOrdenes.EntidadEstadoEntregaOrden;
import angel_zero.inventario.historialOrdenes.EntidadEstadoPagoOrden;
import angel_zero.inventario.historialOrdenes.EntidadHistorialOrdenes;
import angel_zero.inventario.historialOrdenes.RepositorioEstadoEntrega;
import angel_zero.inventario.historialOrdenes.RepositorioEstadoPago;
import angel_zero.inventario.historialOrdenes.RepositorioHistorialOrdenes;
import angel_zero.inventario.marcas.EntidadMarcas;
import angel_zero.inventario.marcas.RepositorioMarcas;
import angel_zero.inventario.ordenesProductos.EntidadOrdenesProductos;
import angel_zero.inventario.ordenesProductos.RepositorioEnvios;
import angel_zero.inventario.ordenesProductos.RepositorioOrdenesProductos;
import angel_zero.inventario.pagos.DTOCuentaPayPal;
import angel_zero.inventario.pagos.DTODatosTarjeta;
import angel_zero.inventario.pagos.DTORegistarMetodoPago;
import angel_zero.inventario.pagos.EntidadCuentaPayPal;
import angel_zero.inventario.pagos.EntidadMetodosPago;
import angel_zero.inventario.pagos.EntidadTarjetaPago;
import angel_zero.inventario.pagos.EntidadTipoTarjeta;
import angel_zero.inventario.pagos.RepositorioMetodosPago;
import angel_zero.inventario.pagos.RepositorioTiposTarjeta;
import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.productos.RepositorioProductos;
import angel_zero.inventario.proveedores.EntidadProveedores;
import angel_zero.inventario.proveedores.RepositorioProveedores;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class InventarioBackendBaseDeDatosApplication implements CommandLineRunner{
	
	private final RepositorioRoles repoRoles;
	private final RepositorioAdmins repoAdmins;
	private final RepositorioClientes repoClientes;
	private final RepositorioProveedores repoProveedores;
	private final RepositorioCategorias repoCategorias;
	private final RepositorioMarcas repoMarcas;
	private final RepositorioProductos repoProductos;
	private final RepositorioRolesUsuarios repoRolUsu;
	private final RepositorioEstados repoEstados;
	private final RepositorioMunicipiosAlcaldias repoMunicipiosAlcaldias;
	private final RepositorioEstadoPago repoEstadoPago;
	private final RepositorioEstadoEntrega repoEstadoEntrega;
	private final RepositorioDirecciones repoDirecciones;
	private final RepositorioMetodosPago repoMetodosPago;
	private final RepositorioTiposTarjeta repoTiposTarjeta;
	private final RepositorioOrdenesProductos repoOrdenes;
	private final RepositorioHistorialOrdenes repoHistorialOrdenes;
	private final RepositorioEnvios repoEnvios;

	public static void main(String[] args) {
		SpringApplication.run(InventarioBackendBaseDeDatosApplication.class, args);
		/*
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerSubtypes(DTOCuentaPayPal.class, DTODatosTarjeta.class);

		String json = "{ \"propietarioMetodo\": \"Ana López\", \"calleFacturacion\": \"Calle 456\", \"tipoPago\": \"PAYPAL\", \"email\": \"ana.paypal@email.com\" }";
		 
		DTORegistarMetodoPago metodoPago;
		try {
			metodoPago = mapper.readValue(json, DTORegistarMetodoPago.class);
			System.out.println(metodoPago);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		if (repoEstados.count() == 0) {
			
			repoEstados.save(new EntidadEstados("Ciudad de México", "CDMX"));
			repoEstados.save(new EntidadEstados("Estado de México", "MEX"));
			
		}
		
		if (repoMunicipiosAlcaldias.count() == 0) {
			
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Álvaro Obregón", "AOB", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Azcapotzalco", "AZC", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Benito Juárez", "BEJ", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Coyoacán", "COY", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Cuajimalpa de Morelos", "CUA", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Cuauhtémoc", "CUH", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Gustavo A. Madero", "GAM", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Iztacalco", "IZC", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Iztapalapa", "IZP", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("La Magdalena Contreras", "LMC", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Miguel Hidalgo", "MIH", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Milpa Alta", "MIA", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tláhuac", "TLA", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tlalpan", "TLP", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Venustiano Carranza", "VCA", repoEstados.findById(1L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Xochimilco", "XOC", repoEstados.findById(1L).get()));
			
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Acambay de Ruiz Castañeda", "ARC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Acolman", "ACO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Aculco", "ACU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Almoloya de Alquisiras", "ADA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Almoloya de Juárez", "ADJ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Almoloya del Río", "ADR", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Amanalco", "AMA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Amatepec", "AMP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Amecameca", "AMC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Apaxco", "APX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Atenco", "ATC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Atizapán", "ATZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Atizapán de Zaragoza", "ATZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Atlacomulco", "ATL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Atlautla", "ATU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Axapusco", "AXP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ayapango", "AYA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Calimaya", "CAL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Capulhuac", "CAP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Coacalco de Berriozábal", "CAB", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Coatepec Harinas", "COH", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Cocotitlán", "CCT", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Coyotepec", "COY", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Cuautitlán", "CUA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chalco", "CHA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chapa de Mota", "CDM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chapultepec", "CHP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chiautla", "CHI", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chicoloapan", "CBN", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chiconcuac", "CCU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Chimalhuacán", "CHM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Donato Guerra", "DOG", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ecatepec de Morelos", "ECM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ecatzingo", "ECZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Huehuetoca", "HUE", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Hueypoxtla", "HUX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Huixquilucan", "HUI", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Isidro Fabela", "ISF", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ixtapaluca", "IXP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ixtapan de la Sal", "IXS", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ixtapan del Oro", "IXO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ixtlahuaca", "IXL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Xalatlaco", "XAL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Jaltenco", "JAL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Jilotepec", "JIL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Jilotzingo", "JIZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Jiquipilco", "JIQ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Jocotitlán", "JOC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Joquicingo", "JOQ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Juchitepec", "JUC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Lerma", "LER", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Malinalco", "MAL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Melchor Ocampo", "MOC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Metepec", "MET", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Mexicaltzingo", "MEX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Morelos", "MOR", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Naucalpan de Juárez", "NAU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Nezahualcóyotl", "NEZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Nextlalpan", "NEX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Nicolás Romero", "NIC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Nopaltepec", "NOP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ocoyoacac", "OCO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ocuilan", "OCU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("El Oro", "ORO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Otumba", "OTU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Otzoloapan", "OTZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Otzolotepec", "OTO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Ozumba", "OZU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Papalotla", "PAP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("La Paz", "PAZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Polotitlán", "POL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Rayón", "RAY", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("San Antonio la Isla", "SAI", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("San Felipe del Progreso", "SFP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("San Martín de las Pirámides", "SMP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("San Mateo Atenco", "SMA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("San Simón de Guerrero", "SSG", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Santo Tomás", "STO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Soyaniquilpan de Juárez", "SOJ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Sultepec", "SUL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tecámac", "TEC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tejupilco", "TEJ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Temamatla", "TEM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Temascalapa", "TEA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Temascalcingo", "TEE", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Temascaltepec", "TET", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Temoaya", "TOA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tenancingo", "TEN", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tenango del Aire", "TEA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tenango del Valle", "TEV", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Teoloyucan", "TEO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Teotihuacán", "TEO", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tepetlaoxtoc", "TEP", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tepetlixpa", "TEX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tepotzotlán", "TET", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tequixquiac", "TEQ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Texcaltitlán", "TEX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Texcalyacac", "TEA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Texcoco", "TEX", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tezoyuca", "TEZ", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tianguistenco", "TIA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Timilpan", "TIM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tlalmanalco", "TLA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tlalnepantla de Baz", "TLB", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tlatlaya", "TTA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Toluca", "TOL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tonatico", "TON", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tultepec", "TUL", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tultitlán", "TUT", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Valle de Bravo", "VDB", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Villa de Allende", "VDA", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Villa del Carbón", "VDC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Villa Guerrero", "VGU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Villa Victoria", "VVI", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Xonacatlán", "XON", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Zacazonapan", "ZAC", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Zacualpan", "ZAU", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Zinacantepec", "ZIN", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Zumpahuacán", "ZUM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Zumpango", "ZUM", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Cuautitlán Izcalli", "CUI", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Valle de Chalco Solidaridad", "VCS", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Luvianos", "LUV", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("San José del Rincón", "SJR", repoEstados.findById(2L).get()));
			repoMunicipiosAlcaldias.save(new EntidadMunicipiosAlcaldias("Tonanitla", "TON", repoEstados.findById(2L).get()));
			
		}
		
		if (repoRoles.findAll().isEmpty()) {
			
			repoRoles.save(new EntidadRoles("administrador"));
			repoRoles.save(new EntidadRoles("proovedor"));
			repoRoles.save(new EntidadRoles("cliente"));
			
		}
		
		if (repoCategorias.count() == 0) {
			
			repoCategorias.save(new EntidadCategoria("gaseosa"));
			repoCategorias.save(new EntidadCategoria("lácteo"));
			repoCategorias.save(new EntidadCategoria("jugos / néctares"));
			repoCategorias.save(new EntidadCategoria("derivado de carne"));
			
		}
		
		if (repoProveedores.count() == 0) {
			
			repoProveedores.save(new EntidadProveedores("DeliVidaCorp", "3249821667"));
			repoProveedores.save(new EntidadProveedores("Saboris_SA", "7316945864"));
			
			List <EntidadRoles> rolesProovedor = new ArrayList<EntidadRoles>();
			rolesProovedor.add(repoRoles.findByNombreRol("proovedor"));
			
			repoMarcas.save(new EntidadMarcas(repoProveedores.findById(1L).get(), "Creamisima"));
			repoMarcas.save(new EntidadMarcas(repoProveedores.findById(1L).get(), "Juicera"));
			
			repoMarcas.save(new EntidadMarcas(repoProveedores.findById(2L).get(), "Carneluxe"));
			repoMarcas.save(new EntidadMarcas(repoProveedores.findById(2L).get(), "Fizzano"));
			
			repoProductos.save(new EntidadProductos("ButterSoft", 45.00, 10, 
					repoMarcas.findById(1L).get(), repoCategorias.findById(2L).get()));
			repoProductos.save(new EntidadProductos("MilkJoy", 50.00, 10, 
					repoMarcas.findById(1L).get(), repoCategorias.findById(2L).get()));
			repoProductos.save(new EntidadProductos("WhipEase", 60.50, 10, 
					repoMarcas.findById(1L).get(), repoCategorias.findById(2L).get()));
			repoProductos.save(new EntidadProductos("CheesyLove", 80.00, 10, 
					repoMarcas.findById(1L).get(), repoCategorias.findById(2L).get()));
			repoProductos.save(new EntidadProductos("YogZest", 40.00, 10, 
					repoMarcas.findById(1L).get(), repoCategorias.findById(2L).get()));
			repoProductos.save(new EntidadProductos("OrangePure", 30.00, 10, 
					repoMarcas.findById(2L).get(), repoCategorias.findById(3L).get()));
			repoProductos.save(new EntidadProductos("BerryMix+", 45.00, 10, 
					repoMarcas.findById(2L).get(), repoCategorias.findById(3L).get()));
			repoProductos.save(new EntidadProductos("CitrusFlow", 30.50, 10, 
					repoMarcas.findById(2L).get(), repoCategorias.findById(3L).get()));
			repoProductos.save(new EntidadProductos("TropicalSplash", 50.00, 10, 
					repoMarcas.findById(2L).get(), repoCategorias.findById(3L).get()));
			repoProductos.save(new EntidadProductos("GreenBoost", 70.00, 10, 
					repoMarcas.findById(2L).get(), repoCategorias.findById(3L).get()));
			
			repoProductos.save(new EntidadProductos("Jamón Royale", 65.70, 10, 
					repoMarcas.findById(3L).get(), repoCategorias.findById(4L).get()));
			repoProductos.save(new EntidadProductos("SalchiFest", 50.00, 10, 
					repoMarcas.findById(3L).get(), repoCategorias.findById(4L).get()));
			repoProductos.save(new EntidadProductos("ChorizoGourmet", 100.50, 10, 
					repoMarcas.findById(3L).get(), repoCategorias.findById(4L).get()));
			repoProductos.save(new EntidadProductos("PavoDelis", 77.00, 10, 
					repoMarcas.findById(3L).get(), repoCategorias.findById(4L).get()));
			repoProductos.save(new EntidadProductos("MortadiGold", 80.00, 10, 
					repoMarcas.findById(3L).get(), repoCategorias.findById(4L).get()));
			repoProductos.save(new EntidadProductos("NaranZest", 35.50, 10, 
					repoMarcas.findById(4L).get(), repoCategorias.findById(1L).get()));
			repoProductos.save(new EntidadProductos("TropiMix", 50.00, 10, 
					repoMarcas.findById(4L).get(), repoCategorias.findById(1L).get()));
			repoProductos.save(new EntidadProductos("ManzanaViva", 32.50, 10, 
					repoMarcas.findById(4L).get(), repoCategorias.findById(1L).get()));
			repoProductos.save(new EntidadProductos("BerryRush", 37.50, 10, 
					repoMarcas.findById(4L).get(), repoCategorias.findById(1L).get()));
			repoProductos.save(new EntidadProductos("CítrikoPower", 35.50, 10, 
					repoMarcas.findById(4L).get(), repoCategorias.findById(1L).get()));
			
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(repoProveedores.findById(1L).get(), rolesProovedor, 
					"DeliVidaCorp@example.com", "$2a$10$OJz/pYX5dhtb2CACV7ThL.D/RBKsWVXRY3MSNVyUgd0e6Znxs6KAO"));
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(repoProveedores.findById(2L).get(), rolesProovedor, 
					"Saboris_SA@example.com", "$2a$10$zLjrZfYGlJeTp0zA0laANeGWLL.FvV2urmEVVWqBTWFBFDSg3bfiW"));
		}
		
		if (repoAdmins.count() == 0) {
			
			List <EntidadRoles> rolesAdministrador = new ArrayList<EntidadRoles>();
			rolesAdministrador.add(repoRoles.findByNombreRol("cliente"));
			rolesAdministrador.add(repoRoles.findByNombreRol("proovedor"));
			rolesAdministrador.add(repoRoles.findByNombreRol("administrador"));
			
			EntidadAdmins administrador = repoAdmins.save(new EntidadAdmins("superAdministrado"));
			
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(administrador, rolesAdministrador,
					"superadministradore@example.com", "$2a$10$.UYgcZuHE7muCbzoiAdDROPVC6ndwZgdI5kPAy5eFm6Sh6LA8Zk2S"));
			
		}
		
		if (repoClientes.count() == 0) {
			
			//DTORegistroNuevoCliente cliente = new DTORegistroNuevoCliente();
			
			List <EntidadRoles> rolesCliente = new ArrayList<EntidadRoles>();
			rolesCliente.add(repoRoles.findByNombreRol("cliente"));
			
			EntidadClientes cliente1 = repoClientes.save(new EntidadClientes("Nathiel", "Drake", null, "46325792"));
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(cliente1, rolesCliente,
					"nathiel_drake@example.com", "$2a$10$2DQdLYb./zQ5V.Xp77dw5Oy3ORsPiDtWbbVxyw47rSsu0LHKIb/Wq"));
			
			EntidadClientes cliente2 = repoClientes.save(new EntidadClientes("Jin", "Sakai", null, "98651347"));
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(cliente2, rolesCliente,
					"jin_sakai@example.com", "$2a$10$TEP.nY1ZH0l8u7v8t1svjuXyxMpWFjZ.ffy4lwDJ/qJkX5ID0upDu"));
			
			EntidadClientes cliente3 = repoClientes.save(new EntidadClientes("Limbo", "Lotus", null, "46852731"));
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(cliente3, rolesCliente,
					"el_viajador@example.com", "$2a$10$rc2uDIMO8x5VKJhuPBc17e1xuxUEwDpMk4hNfkZb3tzpKt6DZAR9G"));
			
			
			
		}
		
		
		if (repoEstadoPago.count() == 0) {
			
			repoEstadoPago.save(new EntidadEstadoPagoOrden("inactivo"));
			repoEstadoPago.save(new EntidadEstadoPagoOrden("en espera"));
			repoEstadoPago.save(new EntidadEstadoPagoOrden("rechazado"));
			repoEstadoPago.save(new EntidadEstadoPagoOrden("cancelado"));
			repoEstadoPago.save(new EntidadEstadoPagoOrden("finalizado"));
			
		}
		
		if (repoEstadoEntrega.count() == 0) {

			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("en camino"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("entregado"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("entrega inatendida"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("no recibido"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("extraviado"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("falto de pago"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("cancelado"));
			
		}
		
		if (repoDirecciones.count() == 0) {
			
			repoDirecciones.save(new EntidadDirecciones("Las flores", "tlalpan centro", "14000", 
					repoMunicipiosAlcaldias.encontrarMunicipio("tlalpan").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.4326", "-99.1546", repoProveedores.findById(1L).get()));
			repoDirecciones.save(new EntidadDirecciones("A.V de los insurgentes sur", "Coyoacán", "04510", 
					repoMunicipiosAlcaldias.encontrarMunicipio("coyoacan").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.3312", "-99.1923", repoProveedores.findById(1L).get()));
			repoDirecciones.save(new EntidadDirecciones("A.V. Paseo de la Reforma", "Bosque de Chapultepec", "11580", 
					repoMunicipiosAlcaldias.encontrarMunicipio("miguel").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.4233", "-99.1808", repoProveedores.findById(1L).get()));
			
			repoDirecciones.save(new EntidadDirecciones("Balderas", "Colonia Centro", "06040", 
					repoMunicipiosAlcaldias.encontrarMunicipio("cuauh").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.430782479332542", "-99.14950605921115", repoProveedores.findById(2L).get()));
			repoDirecciones.save(new EntidadDirecciones("Seminario 8", "Centro historico de Cdad de Méx", "06060", 
					repoMunicipiosAlcaldias.encontrarMunicipio("cuauh").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.43463475267145", "-99.13188590833872", repoProveedores.findById(2L).get()));
			
			repoDirecciones.save(new EntidadDirecciones("Zapotitla 3", "San Juan", "16000", 
					repoMunicipiosAlcaldias.encontrarMunicipio("xochi").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.268284808213405", "-99.107825316211", true, repoClientes.findById(1L).get()));
			repoDirecciones.save(new EntidadDirecciones("Juan Escutia", "Villa Luvianos", "51440", 
					repoMunicipiosAlcaldias.encontrarMunicipio("Luvianos").get(), repoEstados.encontrarEstadoPorCadena("estado").get(),
					null, "18.918411207425265", "-100.302125034343461", true, repoClientes.findById(2L).get()));
			repoDirecciones.save(new EntidadDirecciones("Avenida Venustiano Carranza", "Cabecera Municipal", "56330", 
					repoMunicipiosAlcaldias.encontrarMunicipio("Chima").get(), repoEstados.encontrarEstadoPorCadena("estado").get(),
					null, "19.416240342228186", "-98.94124280919195", true, repoClientes.findById(3L).get()));

		}
		
		if (repoTiposTarjeta.count() == 0) {
			
			repoTiposTarjeta.save(new EntidadTipoTarjeta("CRÉDITO"));
			repoTiposTarjeta.save(new EntidadTipoTarjeta("DÉBITO"));
		}
		
		if (repoMetodosPago.findAll().isEmpty()) {
			
			/*
			EntidadClientes cliente = repoClientes.findById(1L).orElseThrow(
					() -> new RuntimeException("No se encontró el usuaio"));
			*/
			
			EntidadTipoTarjeta tarjetaCredito = repoTiposTarjeta.findById(1L).get();
			EntidadTipoTarjeta tarjetaDebito = repoTiposTarjeta.findById(2L).get();
			
			repoMetodosPago.save(new EntidadTarjetaPago("Nathiel Dreake", "Zapotitla 3", repoClientes.findById(1L).get(), true,
					"1647952346879510", "2033-08", "000", BigDecimal.valueOf(2000.5), tarjetaCredito));
			repoMetodosPago.save(new EntidadTarjetaPago("Jin Sakai", "Juan Escutia", repoClientes.findById(2L).get(), true,
					"9735846123456799", "2033-10", "777", BigDecimal.valueOf(1000.7), tarjetaDebito));
			repoMetodosPago.save(new EntidadCuentaPayPal("el_viajador@example.com", "El viajador", repoClientes.findById(3L).get(), true));
			
			
		}
		
		if (repoHistorialOrdenes.count() == 0) {
			
			/*
			EntidadProductos producto1 = repoProductos.getReferenceById(1L);
			EntidadProductos producto15 = repoProductos.getReferenceById(15L);
			EntidadOrdenesProductos ordenProducto1 = repoOrdenes.save(new EntidadOrdenesProductos(producto1, 2));
			EntidadOrdenesProductos ordenProducto2 = repoOrdenes.save(new EntidadOrdenesProductos(producto15, 3));
			
			List <EntidadOrdenesProductos> listaProducto = List.of(ordenProducto1, ordenProducto2);
			*/
			
			for (int i = 1; i < 4; i++) {
				
				Long numeroProductoAleatorio1 = (long) (Math.random() * 20) + 1;
				Long numeroProductoAleatorio2 = (long) (Math.random() * 20) + 1;
				int cantidadAleatoriaProducto1 = (int) (Math.random() * 6 ) + 1;
				int cantidadAleatoriaProducto2 = (int) (Math.random() * 6 ) + 1;
				int numeroAleatorioDia = (int) (Math.random() * 30) + 1;
				int horaAleatoriaDia = (int) (Math.random() * 14) + 9;
				int minutoAleatorioDia = (int) (Math.random() * 59) + 1;
				
				String dia = "";
				String hora = "";
				String minuto = "";
				
				if (numeroAleatorioDia < 10) {
					
					dia = "0" + numeroAleatorioDia;
					
				} else {
					
					dia = String.valueOf(numeroAleatorioDia);
					
				}
				
				if (horaAleatoriaDia < 10) {
					
					hora = "0" + horaAleatoriaDia;
					
				} else {
					
					hora = String.valueOf(horaAleatoriaDia);
					
				}
				
				if (minutoAleatorioDia < 10) {
					
					minuto = "0" + minutoAleatorioDia;
					
				} else {
					
					minuto = String.valueOf(minutoAleatorioDia);
					
				}
				
				String fecha = "2025-06-" + dia + 
						"T" + hora + ":" + minuto + ":00";
				
				LocalDateTime fechaOrden = LocalDateTime.parse(fecha);
				
				
				
				EntidadProductos producto1 = repoProductos.getReferenceById(numeroProductoAleatorio1);
				EntidadProductos producto2 = repoProductos.getReferenceById(numeroProductoAleatorio2);
				EntidadOrdenesProductos ordenProducto1 = repoOrdenes.save(new EntidadOrdenesProductos(producto1, cantidadAleatoriaProducto1));
				EntidadOrdenesProductos ordenProducto2 = repoOrdenes.save(new EntidadOrdenesProductos(producto2, cantidadAleatoriaProducto2));
				
				List <EntidadOrdenesProductos> listaProducto = List.of(ordenProducto1, ordenProducto2);
				
				EntidadHistorialOrdenes orden = new EntidadHistorialOrdenes(); 
				
				orden.setFechaInicioOrden(fechaOrden);
				orden.setFechaFiniquitpOrden(null);
				orden.setOrdenCliente(repoClientes.findById((long) i).get());
				orden.setActiva(true);
				orden.setNumeroDeOrden(listaProducto);
				orden.setTotalAPagar(orden.calcularTotalAPagar());
				orden.setEstadoTransaccion(repoEstadoPago.getReferenceById(1L));
				orden.setEstadoPaquete(null);
				
				repoHistorialOrdenes.save(orden);
				
				listaProducto.get(0).setIdOrdenHistorial(orden);
				listaProducto.get(1).setIdOrdenHistorial(orden);
				/*
				ordenProducto1.setIdOrdenHistorial(primeraOrden);
				ordenProducto2.setIdOrdenHistorial(primeraOrden);
				*/
				
				
			}
			
			
		}
		
	}

}
