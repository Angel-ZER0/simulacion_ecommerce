package angel_zero.inventario.configSeguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSeguridad {

	private final FiltroSeguridad filtro;
	
	private static final String [] URLS_PUBLICAS_ACCESO = {"/clientes/accesar", "/clientes/registrar", 
			"/proveedor/registro-empresa", "/proveedor/acceso-proveedor", "/administracion/inicio-sesion"};
	private static final String [] URLS_ADMINS = {"/administracion/registrar-administrador",
			"/soporte/administracion/inicio-sesion"};
	//private static final String [] URLS_PRODUCTOS = {"/productos", "/productos/buscar"};
	private static final String [] ADM_PRO = {"administrador", "proovedor"};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.POST, URLS_PUBLICAS_ACCESO).permitAll()
						.requestMatchers(HttpMethod.GET, "/productos").permitAll()
						.requestMatchers(HttpMethod.POST, "/productos/buscar").permitAll()
						.requestMatchers(HttpMethod.POST, URLS_ADMINS).hasRole("administrador")					.requestMatchers(HttpMethod.GET, "/").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class).build();
				
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
    public UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.newInstance();
    }
	
}
