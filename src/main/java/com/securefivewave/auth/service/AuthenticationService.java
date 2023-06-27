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
import com.securefivewave.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final IUserRepository<User> userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;

	public RegisterResponse register(RegisterRequest request) {
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))				
				.build();
		userRepository.create(user);
		var jwtToken = jwtService.generateToken(user);
		return RegisterResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		try {
			var user = userRepository.getUserByEmail(request.getEmail())
					.orElseThrow();
			var jwtToken = jwtService.generateToken(user);
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
