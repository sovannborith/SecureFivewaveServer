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
import com.securefivewave.service.implementation.RoleServiceImpl;
import com.securefivewave.service.implementation.UserRoleServiceImpl;
import com.securefivewave.service.implementation.UserServiceImpl;
import com.securefivewave.service.implementation.UserTokenServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserServiceImpl userServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final UserTokenServiceImpl userTokenServiceImpl;
	private final UserRoleServiceImpl userRoleServiceImpl;
	private final RoleServiceImpl roleServiceImpl;
	private final IUserRepository userRepository;

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
			saveUserToken(user,jwtToken); // Save user token
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

	private void saveUserToken(User user, String jwtToken)
	{						
		userTokenServiceImpl.saveUserToken(user,jwtToken);						
	}
	private void revokedAllValidUserTokenByUserId(Long Id)
	{						
		userTokenServiceImpl.revokedAllValidUserTokenByUserId(Id);				
	}

	
}
