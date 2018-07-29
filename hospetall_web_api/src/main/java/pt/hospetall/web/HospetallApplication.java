package pt.hospetall.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import pt.hospetall.web.security.CustomUserDetailsService;

@SpringBootApplication
public class HospetallApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospetallApplication.class, args);
	}

}
