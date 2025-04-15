package angel_zero.inventario.pagos;

import java.util.Random;

public class SimuladorDePago {
	
    public static Long estadoPago() throws InterruptedException {
        Random random = new Random();

        // Generar un número aleatorio entre 1 y 5
        int numeroAleatorio = random.nextInt(5) + 1; // 1-5
        return Long.valueOf(numeroAleatorio);

    }

}
