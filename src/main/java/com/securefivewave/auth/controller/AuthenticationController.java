package com.securefivewave.auth.controller;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.securefivewave.handler.response.RefreshTokenResponse;
import com.securefivewave.handler.response.RegisterResponse;
import com.securefivewave.handler.response.VerifyOtpResponse;
import com.securefivewave.service.implementation.UserOtpServiceImpl;
import com.securefivewave.service.implementation.UserTokenServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")

@RequiredArgsConstructor

public class AuthenticationController {

	private final AuthenticationService authService;
	private final UserTokenServiceImpl tokenServiceImpl;
	private final UserOtpServiceImpl userOtpServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<CommonResponse<RegisterResponse>> register (@RequestBody @Valid RegisterRequest request) throws Exception{
		
		try{
			RegisterResponse res = authService.register(request);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<CommonResponse<AuthenticationResponse>> login (@RequestBody @Valid AuthenticationRequest request){
		try {
			AuthenticationResponse res = authService.authenticate(request);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	/* @PostMapping("/logout")
	public ResponseEntity<CommonResponse<AuthenticationResponse>> logout (){
		try {
			logoutService.logout()
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(''));
		}
		catch(Exception e)
		{
			throw e;
		}
	} */

	@PutMapping("/verifyOtp")
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
			VerifyOtpResponse res =userOtpServiceImpl.regenerateOtp(email);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(res);
		}

		catch(Exception e)
		{
			throw e;
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<RefreshTokenResponse> refreshToken (HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			RefreshTokenResponse token = tokenServiceImpl.refreshToken(request,response);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(token);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
