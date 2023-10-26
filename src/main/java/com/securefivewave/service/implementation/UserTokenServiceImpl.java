
package com.securefivewave.service.implementation;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserToken;
import com.securefivewave.handler.response.RefreshTokenResponse;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.repository.IUserTokenRepository;
import com.securefivewave.service.IUserTokenService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements IUserTokenService {
	
	private final IUserTokenRepository userTokenRepository;
	private final JwtService jwtService;
    private final IUserRepository userRepository;
	
	@Override
	public UserToken createUserToken(UserToken userToken) {		
		return userTokenRepository.save(userToken);		
	}

	@Override
	public UserToken getUserTokenByUserId(Long userId) {
		
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
		/* List<UserToken> validUserTokens = this.getAllValidUserTokenByUserId(userId);
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
    	});
		userTokenRepository.saveAll(validUserTokens); */
	}
	
	public RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException, StreamWriteException, DatabindException, java.io.IOException {

        final String authHeader = request.getHeader(GlobalConstaint.AUTH_HEADER);
		final String refreshToken;
		if(authHeader ==null || !authHeader.startsWith(GlobalConstaint.JWT_PREFIX)) {
			return null;
		}
		refreshToken = authHeader.substring(7);
		try{
			if(refreshToken !=null && refreshToken.length()>0)
			{
				String email = this.jwtService.extractUsername(refreshToken);
				User user = this.userRepository.getUserByEmail(email);
				if(user ==null)
					{
						throw new JwtException("Invalid refresh token");
					}
				UserToken userToken = this.userTokenRepository.getUserTokenByUserId(user.getId());
				if(userToken !=null){
					if(userToken.getRefreshTokenExpiryDate().before(new Date())){
						// Refresh token is expired
						throw new JwtException("Refresh token is expired");
					}
					String newAccessToken = jwtService.generateToken(user.getEmail());
					Date accessTokenExpiryDate = new Date(System.currentTimeMillis()+ this.jwtService.getAccessTokenExpiration());
					userToken.setAccessToken(newAccessToken);
					userToken.setAccessTokenExpiryDate(accessTokenExpiryDate);
					saveUserToken(userToken);

					/* AuthenticationResponse authResponse = AuthenticationResponse.builder()
						.accessToken(newAccessToken)
						.refreshToken(userToken.getRefreshToken())
						.build();
					new ObjectMapper().writeValue(response.getOutputStream(), authResponse); */
					return RefreshTokenResponse.builder()
						.accessToken(newAccessToken)
						.accessTokenExpiryDate(userToken.getAccessTokenExpiryDate())
						.refreshToken(userToken.getRefreshToken())
						.refreshTokenExpiryDate(userToken.getRefreshTokenExpiryDate())
						.success(true)
						.errorCode(null)
						.message("Token refreshed")
						.build();
				}
				else{
					throw new JwtException("Invalid refresh token");
				}
			}
			else{
				throw new JwtException("Invalid refresh token");
			}
		}
		catch(Exception e){
			throw e;
		}
		
    }

	public void saveUserToken(UserToken userToken)
	{
		this.userTokenRepository.save(userToken);												
	}

	@Override
	public UserToken getUserTokenByRefreshToken(String refreshToken) {
		return userTokenRepository.getUserTokenByRefreshToken(refreshToken);
	}
}
