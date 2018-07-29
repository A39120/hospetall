package pt.hospetall.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPasswordEncoder {

	private static int PASSWORD_STRENGTH = 12;
	private static int SECRET_STRENGTH = 1;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new  BCryptPasswordEncoder(PASSWORD_STRENGTH);
	}

	/*@Bean
	public PasswordEncoder secretPasswordEncoder(){
		return new BCryptPasswordEncoder(SECRET_STRENGTH);
	}*/

}
