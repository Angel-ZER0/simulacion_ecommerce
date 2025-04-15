package angel_zero.inventario.configSeguridad;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioEncriptarContrasena {

	private final PasswordEncoder passwordEncoder;
	
	public String contrasenaEncriptada(String contrasena) {
		
		return passwordEncoder.encode(contrasena);
		
	}
	
}
