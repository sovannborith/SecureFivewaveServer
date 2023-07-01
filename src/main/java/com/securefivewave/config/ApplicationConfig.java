package com.securefivewave.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.securefivewave.auth.service.SecureFivewaveUserDetail;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.dto.dtomapper.UserDTOMapper;
import com.securefivewave.service.implementation.UserServiceImpl;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	
	private final UserServiceImpl userServiceImpl;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(GlobalConstaint.PASSWORD_STRENGTH);
	}
	@Bean
	UserDetailsService userDetailsService() {
		return userName -> new SecureFivewaveUserDetail(UserDTOMapper.toUser(userServiceImpl.getUserByEmail(userName)));
	}
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
		
}
