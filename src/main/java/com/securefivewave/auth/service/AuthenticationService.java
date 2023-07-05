package com.securefivewave.auth.service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securefivewave.auth.AuthenticationRequest;
import com.securefivewave.auth.AuthenticationResponse;
import com.securefivewave.auth.OtpResponse;
import com.securefivewave.auth.RegisterRequest;
import com.securefivewave.auth.RegisterResponse;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserToken;
import com.securefivewave.jwt.JwtService;
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
			//SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user, userRoleServiceImpl, roleServiceImpl);
			String jwtToken = jwtService.generateToken(request.getEmail());
			String refreshToken = jwtService.generateRefreshToken(request.getEmail());
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
				throw new UsernameNotFoundException(GlobalConstaint.LOGIN_FAILED);
			}
			
		}
		catch(Exception e)
		{
			throw new UsernameNotFoundException(GlobalConstaint.LOGIN_FAILED);
		}
	}	
	
	public OtpResponse verifyOtp(String email, String otp)
	{
		try{
			return userOtpServiceImpl.verifyOtp(email, otp);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public OtpResponse resendOtp(String email)
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
}
