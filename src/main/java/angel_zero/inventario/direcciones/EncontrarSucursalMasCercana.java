package angel_zero.inventario.direcciones;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;

public class EncontrarSucursalMasCercana {
	/*
	public static List <String[]> distanciaClienteSucursales(EntidadDirecciones direccionCliente, 
			List <EntidadDirecciones> direccionesSucursales) {
		
		List <String[]> distancias = new ArrayList<String[]>();
		final int RADIO_TIERRA = 6371;
		double radLat1 = Math.toRadians(Double.valueOf(direccionCliente.getLatitud()));
		double radLong1 = Math.toRadians(Double.valueOf(direccionCliente.getLongitud()));
		
		for (EntidadDirecciones direccionSucursal : direccionesSucursales) {
			
			double radLat2 = Math.toRadians(Double.valueOf(direccionSucursal.getLatitud()));
			double radLong2 = Math.toRadians(Double.valueOf(direccionSucursal.getLongitud()));
			double diferenciaRadLatitud = radLat1 - radLat2;
			double diferenciaRadLongitud = radLong1 - radLong2;
			
			double a = Math.pow(Math.sin(diferenciaRadLatitud / 2), 2) +
	                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(diferenciaRadLongitud / 2), 2);
			
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			
			DecimalFormat df = new DecimalFormat("0.0000");
			
			String [] distancia = {direccionSucursal.getId().toString(), df.format(RADIO_TIERRA * c)};
			
			distancias.add(distancia);
			
		}
		
		return distancias;
		
	}
	
	public static String [] arregloDistanciaMasCorta(List <String[]> listaDistancias) {
		
		String[] arregloMenor = listaDistancias.stream()
	            .min(Comparator.comparingDouble(a -> Double.parseDouble(a[1]))).get();
		return arregloMenor;
	
	}
	
	*/
	public static List <DireccionYDistancia> distanciaClienteSucursales(EntidadDirecciones direccionCliente, 
			List <EntidadDirecciones> direccionesSucursales) {
		
		List<DireccionYDistancia> direccionSucursaMasDistancia = new ArrayList<>();
		final int RADIO_TIERRA = 6371;
		double radLat1 = Math.toRadians(Double.valueOf(direccionCliente.getLatitud()));
		double radLong1 = Math.toRadians(Double.valueOf(direccionCliente.getLongitud()));
		
		for (EntidadDirecciones direccionSucursal : direccionesSucursales) {
			
			double radLat2 = Math.toRadians(Double.valueOf(direccionSucursal.getLatitud()));
			double radLong2 = Math.toRadians(Double.valueOf(direccionSucursal.getLongitud()));
			double diferenciaRadLatitud = radLat1 - radLat2;
			double diferenciaRadLongitud = radLong1 - radLong2;
			
			double a = Math.pow(Math.sin(diferenciaRadLatitud / 2), 2) +
	                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(diferenciaRadLongitud / 2), 2);
			
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			
			DecimalFormat df = new DecimalFormat("0.0000");
			
			DireccionYDistancia direccionEncontrada = new DireccionYDistancia(direccionSucursal, df.format(RADIO_TIERRA * c));
			
			direccionSucursaMasDistancia.add(direccionEncontrada);
			
		}
		
		return direccionSucursaMasDistancia;
		
	}
	
	public static DireccionYDistancia arregloDistanciaMasCorta(List <DireccionYDistancia> listaDistancias) {
		
		DireccionYDistancia arregloMenor = listaDistancias.stream()
	            .min(Comparator.comparingDouble(a -> Double.parseDouble((String) a.distancia()))).get();
		return arregloMenor;
	
	}
	
}
