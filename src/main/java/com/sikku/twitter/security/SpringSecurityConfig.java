package com.sikku.twitter.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.sikku.twitter.security.jwt.JwtAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityContextRepository getContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository());
	}

	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager getAuthenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(getBCryptPasswordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

		http.httpBasic(Customizer.withDefaults());

		http.authorizeHttpRequests(
				auth -> auth.requestMatchers(HttpMethod.POST, "auth/login", "auth/register").permitAll())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers(HttpMethod.POST, "auth/test").hasAnyRole("USER", "ADMIN"))
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrfCustomizer -> csrfCustomizer.disable()).cors(cosrCustomizer -> {
					CorsConfigurationSource configurationSource = req -> {
						CorsConfiguration corsConfiguration = new CorsConfiguration();
						corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
						corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
						corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
						corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
						corsConfiguration.setMaxAge(5 * 3600L);
						return corsConfiguration;
					};
					cosrCustomizer.configurationSource(configurationSource);
				}).securityContext(context -> context.requireExplicitSave(true))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
