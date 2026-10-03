package angel_zero.inventario.direcciones;

public enum Estados {

    AGUASCALIENTES("Aguascalientes", "AGS"),
    BAJA_CALIFORNIA("Baja California", "BCN"),
    BAJA_CALIFORNIA_SUR("Baja California Sur", "BCS"),
    CAMPECHE("Campeche", "CAM"),
    CHIAPAS("Chiapas", "CHP"),
    CHIHUAHUA("Chihuahua", "CHI"),
    CIUDAD_DE_MEXICO("Ciudad de México", "CDMX"),
    COAHUILA("Coahuila", "COA"),
    COLIMA("Colima", "COL"),
    DURANGO("Durango", "DUR"),
    GUANAJUATO("Guanajuato", "GTO"),
    GUERRERO("Guerrero", "GRO"),
    HIDALGO("Hidalgo", "HGO"),
    JALISCO("Jalisco", "JAL"),
    MEXICO("México", "MEX"),
    MICHOACAN("Michoacán", "MIC"),
    MORELOS("Morelos", "MOR"),
    NAYARIT("Nayarit", "NAY"),
    NUEVO_LEON("Nuevo León", "NLE"),
    OAXACA("Oaxaca", "OAX"),
    PUEBLA("Puebla", "PUE"),
    QUERETARO("Querétaro", "QRO"),
    QUINTANA_ROO("Quintana Roo", "ROO"),
    SAN_LUIS_POTOSI("San Luis Potosí", "SLP"),
    SINALOA("Sinaloa", "SIN"),
    SONORA("Sonora", "SON"),
    TABASCO("Tabasco", "TAB"),
    TAMAULIPAS("Tamaulipas", "TAM"),
    TLAXCALA("Tlaxcala", "TLX"),
    VERACRUZ("Veracruz", "VER"),
    YUCATAN("Yucatán", "YUC"),
    ZACATECAS("Zacatecas", "ZAC");

    private final String nombreCompleto;
    private final String abreviatura;

    // Constructor del enum
    Estados(String nombreCompleto, String abreviatura) {
        this.nombreCompleto = nombreCompleto;
        this.abreviatura = abreviatura;
    }

    // Método para obtener la abreviatura
    public String getAbreviatura() {
        return abreviatura;
    }

    // Sobrescribir el método toString para mostrar la abreviatura
    @Override
    public String toString() {
        return abreviatura;
    }

    // Método opcional para buscar por nombre completo
    public static Estados fromNombre(String nombre) {
        for (Estados estado : Estados.values()) {
            if (estado.nombreCompleto.equalsIgnoreCase(nombre)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("No se encontró un estado con el nombre: " + nombre);
    }
	
}
