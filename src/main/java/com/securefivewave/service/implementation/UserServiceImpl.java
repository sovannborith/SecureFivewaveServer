/**
 * 
 */
package com.securefivewave.service.implementation;

import org.springframework.stereotype.Service;

import com.securefivewave.domain.User;
import com.securefivewave.dto.UserDTO;
import com.securefivewave.dtomapper.UserDTOMapper;
import com.securefivewave.repository.UserRepository;
import com.securefivewave.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author User
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository<User> userRepository;

	@Override
	public UserDTO createUser(User user) {
		return UserDTOMapper.fromUser(userRepository.create(user));
	}

}
