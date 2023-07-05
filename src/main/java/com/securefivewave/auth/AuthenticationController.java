package com.securefivewave.auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securefivewave.auth.service.AuthenticationService;
import com.securefivewave.service.implementation.UserOtpServiceImpl;
import com.securefivewave.service.implementation.UserTokenServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

	private final AuthenticationService authService;
	private final UserTokenServiceImpl tokenServiceImpl;
	private final UserOtpServiceImpl userOtpServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register (@RequestBody @Valid RegisterRequest request) throws Exception{
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

	@PutMapping("/verify_otp")
	public ResponseEntity<OtpResponse> verifyOtp (@RequestBody @Valid @RequestParam String email, @RequestParam String otp) {
		try {			
			return ResponseEntity.ok(authService.verifyOtp(email, otp));
		}

		catch(Exception e)
		{
			throw e;
		}
	}

	@PutMapping("/resend_otp")
	public ResponseEntity<OtpResponse> resendOtp (@RequestBody @Valid @RequestParam String email) {
		try {			
			
			return ResponseEntity.ok(userOtpServiceImpl.regenerateOtp(email));
		}

		catch(Exception e)
		{
			throw e;
		}
	}

	@PostMapping("/refresh_token")
	public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			tokenServiceImpl.refreshToken(request,response);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
