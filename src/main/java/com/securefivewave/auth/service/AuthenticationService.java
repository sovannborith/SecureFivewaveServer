package com.securefivewave.auth.service;


import java.time.LocalDateTime;
import java.time.Duration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securefivewave.auth.AuthenticationRequest;
import com.securefivewave.auth.AuthenticationResponse;
import com.securefivewave.auth.OtpResponse;
import com.securefivewave.auth.RegisterRequest;
import com.securefivewave.auth.RegisterResponse;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserOtp;
import com.securefivewave.jwt.JwtService;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.implementation.RoleServiceImpl;
import com.securefivewave.service.implementation.UserOtpServiceImpl;
import com.securefivewave.service.implementation.UserRoleServiceImpl;
import com.securefivewave.service.implementation.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserServiceImpl userServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final UserRoleServiceImpl userRoleServiceImpl;
	private final RoleServiceImpl roleServiceImpl;
	private final IUserRepository userRepository;
	private final UserOtpServiceImpl userOtpServiceImpl;

	public RegisterResponse register(RegisterRequest request) throws Exception {
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
			userServiceImpl.createUser(user);// Save registered user
			SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user, userRoleServiceImpl, roleServiceImpl);
			String jwtToken = jwtService.generateToken(userDetails);
			String refreshToken = jwtService.generateRefreshToken(userDetails);
			return RegisterResponse.builder()
					.accessToken(jwtToken)
					.refreschToken(refreshToken)
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
			User user = userRepository.getUserByEmail(request.getEmail());
			SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user, userRoleServiceImpl, roleServiceImpl);
			String jwtToken = jwtService.generateToken(userDetails);
			String refreshToken = jwtService.generateRefreshToken(userDetails);
			/* UserToken userToken = UserToken.builder()
					. */
			return AuthenticationResponse.builder()
					.accessToken(jwtToken)
					.refreschToken(refreshToken)
					.build();
		}
		catch(Exception e)
		{
			throw(e);
		}
	}	
	public OtpResponse verifyOtp(String email, String otp)
	{
		try{
			User user = userRepository.getUserByEmail(email);
			
			if(user !=null)
			{
				if(user.getIsEnable()){
					return OtpResponse.builder()
							.result(GlobalConstaint.USER_ACCOUNT_ALREADY_VERIFIED)
							.build();
				}
				UserOtp userOtp = userOtpServiceImpl.getUserOtpByUserId(user.getId());
				if(userOtp ==null){
					return OtpResponse.builder()
							.result(GlobalConstaint.INVALID_EMAIL_ADDRESS)
							.build();
				}
				
				if(userOtp.getUserOtp().equals(otp)){
					Long timeDiff =Duration.between(LocalDateTime.now(), userOtp.getOtpExpiredAt()).toMillis();
					if(timeDiff<=0){
					// Otp is already expired
					return OtpResponse.builder()
							.result(GlobalConstaint.OTP_IS_EXPIRED)
							.build();
					}
					//Update user to enabled
					user.setIsEnable(true);
					userServiceImpl.update(user);
					return OtpResponse.builder()
							.result(GlobalConstaint.USER_ACCOUNT_IS_VERIFIED)
							.build();
				}
				else{
					return OtpResponse.builder()
							.result(GlobalConstaint.OTP_IS_INVALID)
							.build();
				}
			}
			else{
				return OtpResponse.builder()
				.result(GlobalConstaint.INVALID_EMAIL_ADDRESS)
				.build();
			}
		}
		catch (Exception e){
			throw e;
		}
	}
}
