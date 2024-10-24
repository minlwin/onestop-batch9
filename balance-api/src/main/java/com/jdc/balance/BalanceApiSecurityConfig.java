package com.jdc.balance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import com.jdc.balance.model.BaseRepositoryImpl;
import com.jdc.balance.security.ApiSecurityExceptionHandler;
import com.jdc.balance.security.JwtTokenAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class BalanceApiSecurityConfig {
	
	private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
	private final ApiSecurityExceptionHandler apiSecurityExceptionHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable());
		http.cors(cors -> {});
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authorizeHttpRequests(request -> {
			request.requestMatchers("/security/**").permitAll();
			request.requestMatchers("/account**").hasAuthority("Admin");
			request.requestMatchers("/**").hasAuthority("Member");
		});
		
		http.addFilterAfter(jwtTokenAuthenticationFilter, ExceptionTranslationFilter.class);
		
		http.exceptionHandling(exceptions -> {
			exceptions.accessDeniedHandler(apiSecurityExceptionHandler);
			exceptions.authenticationEntryPoint(apiSecurityExceptionHandler);
		});
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
