package com.securefivewave.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securefivewave.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
private final AuthenticationService authService;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register (@RequestBody @Valid RegisterRequest request){
		log.info("Registering the new user");
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid AuthenticationRequest request){
		try {
			return ResponseEntity.ok(authService.authenticate(request));
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
