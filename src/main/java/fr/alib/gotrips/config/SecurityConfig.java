package fr.alib.gotrips.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.alib.gotrips.filter.AuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private AuthenticationFilter authFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
		.authorizeHttpRequests( authz -> 
			authz
			.requestMatchers("/api/user/delete/**", "/api/**/reservation/create", "/api/**/reservation/get/**").hasAnyRole("USER", "ADMIN")
			.requestMatchers("/api/flight/create", "/api/flight/edit", "/api/flight/delete").hasAnyRole("ADMIN, FLIGHT_COMPANY")
			.requestMatchers("/api/hotel/create", "/api/hotel/edit", "/api/hotel/delete").hasAnyRole("ADMIN, HOTEL_COMPANY")
			.requestMatchers("/api/activity/create", "/api/activity/edit", "/api/activity/delete").hasAnyRole("ADMIN, ACTIVITY_COMPANY")
			.anyRequest().permitAll()
		);
		
		http
		.formLogin(login -> login.disable())
		.logout(logout -> logout.disable())
		.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(csrf -> csrf.disable())
		.httpBasic(basic -> basic.disable());
		
		http.addFilter(authFilter);
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
