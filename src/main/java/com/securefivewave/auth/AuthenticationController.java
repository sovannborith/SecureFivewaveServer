package com.securefivewave.auth;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securefivewave.auth.service.AuthenticationService;
import com.securefivewave.handler.request.AuthenticationRequest;
import com.securefivewave.handler.request.RegisterRequest;
import com.securefivewave.handler.response.AuthenticationResponse;
import com.securefivewave.handler.response.CommonResponse;
import com.securefivewave.handler.response.RegisterResponse;
import com.securefivewave.handler.response.VerifyOtpResponse;
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
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

	private final AuthenticationService authService;
	private final UserTokenServiceImpl tokenServiceImpl;
	private final UserOtpServiceImpl userOtpServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<CommonResponse<RegisterResponse>> register (@RequestBody @Valid RegisterRequest request) throws Exception{
		log.info("Registering the new user");

		try{
			RegisterResponse res = authService.register(request);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
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
	public ResponseEntity<VerifyOtpResponse> verifyOtp (@RequestBody @Valid @RequestParam String email, @RequestParam String otp) {
		try {	
			VerifyOtpResponse verifyOtpResponse =authService.verifyOtp(email, otp);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(verifyOtpResponse);

		}

		catch(Exception e)
		{
			throw e;
		}
	}

	@PutMapping("/resend_otp")
	public ResponseEntity<VerifyOtpResponse> resendOtp (@RequestBody @Valid @RequestParam String email) {
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
