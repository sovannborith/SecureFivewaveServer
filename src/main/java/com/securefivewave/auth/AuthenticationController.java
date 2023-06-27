package com.securefivewave.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securefivewave.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
private final AuthenticationService authService;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register (@RequestBody @Valid RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody @Valid AuthenticationRequest request){
		try {
			return ResponseEntity.ok(authService.authenticate(request));
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
