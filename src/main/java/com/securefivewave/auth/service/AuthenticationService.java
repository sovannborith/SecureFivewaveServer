package com.securefivewave.auth.service;
import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserEvent;
import com.securefivewave.entity.UserToken;
import com.securefivewave.enumeration.EventEnum;
import com.securefivewave.handler.request.AuthenticationRequest;
import com.securefivewave.handler.request.RegisterRequest;
import com.securefivewave.handler.response.AuthenticationResponse;
import com.securefivewave.handler.response.RegisterResponse;
import com.securefivewave.handler.response.VerifyOtpResponse;
import com.securefivewave.jwt.JwtService;
import com.securefivewave.service.implementation.UserEventServiceImpl;
import com.securefivewave.service.implementation.UserOtpServiceImpl;
import com.securefivewave.service.implementation.UserServiceImpl;
import com.securefivewave.service.implementation.UserTokenServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserServiceImpl userServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final UserOtpServiceImpl userOtpServiceImpl;
	private final UserTokenServiceImpl userTokenServiceImpl;
	private final AuthenticationManager authenticationManager;
	private final UserEventServiceImpl userEventServiceImpl;

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
			/* String jwtToken = jwtService.generateToken(request.getEmail());
			String refreshToken = jwtService.generateRefreshToken(request.getEmail()); */
			return RegisterResponse.builder()
					.registerRequest(request)
					.success(true)
					.errorCode(null)
					.message(GlobalConstaint.REGISTER_SUCCESS)
					.build();
		}
		catch(Exception e)
		{
			return RegisterResponse.builder()
					.registerRequest(request)
					.success(false)
					.errorCode(e.hashCode())
					.message(e.getMessage())
					.build();
		}
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try 
		{	
			boolean isLogin = this.getTokenForAuthenticatedUser(request.getEmail(),request.getPassword());
			if(isLogin)
			{
				UserToken userToken = userTokenServiceImpl.getUserTokenByUserId(userServiceImpl.getUserByEmail(request.getEmail()).getId());
				
				String jwtToken = jwtService.generateToken(request.getEmail());
				String refreshToken = jwtService.generateRefreshToken(request.getEmail());

				userToken.setAccessToken(jwtToken);
				userToken.setRefreshToken(refreshToken);
				userToken.setExpired(false);
				userToken.setRevoked(false);
				userTokenServiceImpl.update(userToken);
				
				return AuthenticationResponse.builder()
					.accessToken(jwtToken)
					.refreschToken(refreshToken)
					.build();
			}
			
			else
			{
				UserDTO user = userServiceImpl.getUserByEmail(request.getEmail());
				generateUserEvent(user.getId(),EventEnum.LOGIN_ATTEMP_FAILURE.getType());
				throw new UsernameNotFoundException(GlobalConstaint.LOGIN_FAILED);
			}
			
		}
		catch(Exception e)
		{
			UserDTO user = userServiceImpl.getUserByEmail(request.getEmail());
			generateUserEvent(user.getId(),EventEnum.LOGIN_ATTEMP_FAILURE.getType());
			throw new UsernameNotFoundException(GlobalConstaint.LOGIN_FAILED);
		}
	}	
	
	public VerifyOtpResponse verifyOtp(String email, String otp)
	{
		try{
			return userOtpServiceImpl.verifyOtp(email, otp);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public VerifyOtpResponse resendOtp(String email)
	{
		try{
			return userOtpServiceImpl.regenerateOtp(email);
		}
		catch (Exception e){
			throw e;
		}
	}

	public boolean getTokenForAuthenticatedUser( String userName, String password){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        if (authentication.isAuthenticated()){			
            return true;
        }
        else {
            throw new UsernameNotFoundException(GlobalConstaint.LOGIN_FAILED);
        }
    }

	private void generateUserEvent(Long userId, Long eventId){

		UserEvent userEvent = new UserEvent();
			userEvent.setUserId(userId);
			userEvent.setEventId(eventId);
			userEvent.setDevice(null);
			userEvent.setIpAddress(null);
			userEvent.setCreatedAt(LocalDateTime.now());
		userEventServiceImpl.createUserEvent(userEvent);
	}

}
