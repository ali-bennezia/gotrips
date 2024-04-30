package fr.alib.gotrips.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
		.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(authz->
			authz
				.requestMatchers("/user/register", "/user/signin", "/flight/search?**", "/hotel/search?**", "/activity/search?**").permitAll()
				.requestMatchers("/flight/create", "/flight/{id}", "/flight/edit", "/flight/delete").hasAnyRole("ADMIN", "FLIGHT_COMPANY")
				.requestMatchers("/hotel/create", "/hotel/{id}", "/hotel/edit", "/hotel/delete").hasAnyRole("ADMIN", "HOTEL_COMPANY")
				.requestMatchers("/activity/create", "/activity/{id}", "/activity/edit", "/activity/delete").hasAnyRole("ADMIN", "ACTIVITY_COMPANY")
				
		);
		return http.build();
	}
	
}
