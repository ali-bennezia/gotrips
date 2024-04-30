package fr.alib.gotrips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import fr.alib.gotrips.utils.JWTUtils;

@SpringBootApplication
public class GotripsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GotripsApplication.class, args);
	}

}
