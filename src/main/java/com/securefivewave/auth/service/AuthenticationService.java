package com.securefivewave.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securefivewave.auth.AuthenticationRequest;
import com.securefivewave.auth.AuthenticationResponse;
import com.securefivewave.auth.RegisterRequest;
import com.securefivewave.auth.RegisterResponse;
import com.securefivewave.entity.User;
import com.securefivewave.jwt.JwtService;
import com.securefivewave.service.implementation.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserServiceImpl userServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;

	public RegisterResponse register(RegisterRequest request) {
		try
		{
			User user = User.builder()
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.email(request.getEmail())
					.isEnable(false)
					.isLocked(false)
					.password(passwordEncoder.encode(request.getPassword()))				
					.build();
			userServiceImpl.createUser(user);
			var jwtToken = jwtService.generateToken(request.getEmail());
			return RegisterResponse.builder()
					.token(jwtToken)
					.build();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try 
		{
			authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
			var jwtToken = jwtService.generateToken(request.getEmail());
			return AuthenticationResponse.builder()
					.token(jwtToken)
					.build();
		}
		catch(Exception e)
		{
			throw(e);
		}
	}
}
