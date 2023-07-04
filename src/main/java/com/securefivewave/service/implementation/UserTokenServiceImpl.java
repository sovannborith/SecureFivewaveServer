
package com.securefivewave.service.implementation;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securefivewave.auth.AuthenticationResponse;
import com.securefivewave.auth.service.SecureFivewaveUserDetail;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserToken;
import com.securefivewave.enumeration.TokenTypeEnum;
import com.securefivewave.jwt.JwtService;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.repository.IUserTokenRepository;
import com.securefivewave.service.IUserTokenService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements IUserTokenService{
	
	private final IUserTokenRepository userTokenRepository;
	private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final UserRoleServiceImpl userRoleServiceImpl;
    private final RoleServiceImpl roleServiceImpl;
	
	@Override
	public UserToken createUserToken(UserToken userToken) {		
		return userTokenRepository.save(userToken);		
	}

	@Override
	public List<UserToken> getUserTokenByUserId(Long userId) {
		
		return userTokenRepository.getUserTokenByUserId(userId);
	}

	@Override
	public UserToken getUserTokenById(Long id) {
		Optional<UserToken> userToken = userTokenRepository.findById(id);
		return userToken.orElseThrow();
	}

	@Override
	public UserToken getUserTokenByToken(String token) {
		return userTokenRepository.getUserTokenByToken(token);
	}

	@Override
	public UserToken update(UserToken userToken) {
		return userTokenRepository.save(userToken);
	}

	@Override
	public void deleteById(Long id) {
		userTokenRepository.deleteById(id);
	}

	@Override
	public List<UserToken> getAllValidUserTokenByUserId(Long userId) {
		return userTokenRepository.getAllValidUserTokenByUserId(userId);
	}

	public void revokedAllValidUserTokenByUserId(Long userId)
	{
		List<UserToken> validUserTokens = this.getAllValidUserTokenByUserId(userId);
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
    	});
		userTokenRepository.saveAll(validUserTokens);
	}
	public void refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException, StreamWriteException, DatabindException, java.io.IOException {

        final String authHeader = request.getHeader(GlobalConstaint.AUTH_HEADER);
		final String refreshToken;
		final String userEmail;
		if(authHeader ==null || !authHeader.startsWith(GlobalConstaint.JWT_PREFIX)) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if(userEmail !=null) {
            User user = userRepository.getUserByEmail(userEmail);
			SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user,userRoleServiceImpl,roleServiceImpl );
            
			if(jwtService.isTokenValid(refreshToken, userDetails)) {
				String accessToken = jwtService.generateToken(userDetails);
				revokedAllValidUserTokenByUserId(user.getId());
				saveUserToken(user, refreshToken);
                var authResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreschToken(refreshToken)
                    .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
    }

	public void saveUserToken(User user, String jwtToken)
	{
		UserToken userToken = UserToken.builder()
								.userId(user.getId())
								.accessToken(jwtToken)
								.tokenType(TokenTypeEnum.BEARER.toString())
								.isExpired(false)
								.isRevoked(false)
								.build();
								
		this.createUserToken(userToken);						
	}
}
