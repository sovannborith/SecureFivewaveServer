
package com.securefivewave.service.implementation;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.securefivewave.entity.UserToken;
import com.securefivewave.repository.IUserTokenRepository;
import com.securefivewave.service.IUserTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements IUserTokenService{
	
	private final IUserTokenRepository userTokenRepository;
	
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAllValidUserTokenByUserId'");
	}
	
}
