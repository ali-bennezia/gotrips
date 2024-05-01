package fr.alib.gotrips.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
		.authorizeHttpRequests(authz->
			authz
				.requestMatchers("/user/register", "/user/signin", "/flight/search?**", "/hotel/search?**", "/activity/search?**").permitAll()
				.requestMatchers("/flight/create", "/flight/{id}", "/flight/edit", "/flight/delete").hasAnyRole("ADMIN", "FLIGHT_COMPANY")
				.requestMatchers("/hotel/create", "/hotel/{id}", "/hotel/edit", "/hotel/delete").hasAnyRole("ADMIN", "HOTEL_COMPANY")
				.requestMatchers("/activity/create", "/activity/{id}", "/activity/edit", "/activity/delete").hasAnyRole("ADMIN", "ACTIVITY_COMPANY")
		)
		.formLogin(login -> login.disable())
		.logout(logout -> logout.disable())
		.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(csrf -> csrf.disable())
		.httpBasic(basic -> basic.disable());
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
