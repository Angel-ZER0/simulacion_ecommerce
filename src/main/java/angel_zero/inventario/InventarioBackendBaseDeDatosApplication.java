package angel_zero.inventario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import angel_zero.inventario.historialOrdenes.RepositorioEstadoEntrega;
import angel_zero.inventario.historialOrdenes.RepositorioEstadoPago;
import angel_zero.inventario.marcas.EntidadMarcas;
import angel_zero.inventario.marcas.RepositorioMarcas;
import angel_zero.inventario.pagos.DTOCuentaPayPal;
import angel_zero.inventario.pagos.DTODatosTarjeta;
import angel_zero.inventario.pagos.DTORegistarMetodoPago;
import angel_zero.inventario.pagos.EntidadTipoTarjeta;
import angel_zero.inventario.pagos.RepositorioTiposTarjeta;
import angel_zero.inventario.productos.EntidadProductos;
import angel_zero.inventario.productos.RepositorioProductos;
import angel_zero.inventario.proveedores.EntidadProveedores;
import angel_zero.inventario.proveedores.RepositorioProveedores;
import angel_zero.inventario.rolesPermisos.EntidadRelacionRolesUsuarios;
import angel_zero.inventario.rolesPermisos.EntidadRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRoles;
import angel_zero.inventario.rolesPermisos.RepositorioRolesUsuarios;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class InventarioBackendBaseDeDatosApplication implements CommandLineRunner{
	
	private final RepositorioRoles repoRoles;
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
	private final RepositorioClientes repoClientes;
	private final RepositorioTiposTarjeta repoTiposTarjeta;

	public static void main(String[] args) {
		SpringApplication.run(InventarioBackendBaseDeDatosApplication.class, args);
		
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
		
		
	}

	@Override
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
		
		if (repoClientes.count() == 0) {
			
			//DTORegistroNuevoCliente cliente = new DTORegistroNuevoCliente();
			
			List <EntidadRoles> rolesCliente = new ArrayList<EntidadRoles>();
			rolesCliente.add(repoRoles.findByNombreRol("cliente"));
			
			EntidadClientes cliente = repoClientes.save(new EntidadClientes("Nathiel", "Drake", null, "46325792"));
			
			repoRolUsu.save(new EntidadRelacionRolesUsuarios(cliente, rolesCliente,
					"nathiel_drake@example.com", "$2a$10$2DQdLYb./zQ5V.Xp77dw5Oy3ORsPiDtWbbVxyw47rSsu0LHKIb/Wq"));
			
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
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("denegado"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("extraviado"));
			repoEstadoEntrega.save(new EntidadEstadoEntregaOrden("falto de pagp"));
			
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
			
			repoDirecciones.save(new EntidadDirecciones("Zapotitla 3", "San Juam", "16000", 
					repoMunicipiosAlcaldias.encontrarMunicipio("xochi").get(), repoEstados.encontrarEstadoPorCadena("ciudad").get(),
					null, "19.268284808213405", "-99.107825316211", true, repoClientes.findById(1L).get()));

		}
		
		if (repoTiposTarjeta.count() == 0) {
			
			repoTiposTarjeta.save(new EntidadTipoTarjeta("CRÉDITO"));
			repoTiposTarjeta.save(new EntidadTipoTarjeta("DÉBITO"));
		}
		
	}

}
