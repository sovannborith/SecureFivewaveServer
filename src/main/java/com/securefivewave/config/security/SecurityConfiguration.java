package com.securefivewave.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.jwt.JwtAuthenticationFilter;


import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider ;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
		return http
				.authorizeHttpRequests( auth -> {
                    auth.requestMatchers(GlobalConstaint.UN_SECURED_URLs).permitAll();
                    auth.requestMatchers(GlobalConstaint.SECURED_URLs).hasAnyAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())                
                .sessionManagement(session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS)
                )                
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
		
	}
}
