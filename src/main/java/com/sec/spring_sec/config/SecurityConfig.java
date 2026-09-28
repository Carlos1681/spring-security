package com.sec.spring_sec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private UserDetailsService userDetailsService;

	private JwtFilter jwtFilter;

	public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		return http.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> request.requestMatchers("/register", "/login").permitAll()
						.anyRequest().authenticated())
				// .formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

//	@Bean
//	UserDetailsService userDetailsService() {
//
//		UserDetails user1 = User.withDefaultPasswordEncoder().username("user").password("u123").roles("USER").build();
//
//		UserDetails user2 = User.withDefaultPasswordEncoder().username("admin").password("a123").roles("ADMIN").build();
//
//		return new InMemoryUserDetailsManager(user1, user2);
//	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();
	}

}
