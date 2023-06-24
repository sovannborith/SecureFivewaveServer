/**
 * 
 */
package com.securefivewave.service.implementation;

import org.springframework.stereotype.Service;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.dtomapper.UserDTOMapper;
import com.securefivewave.entity.User;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.IUserService;

import lombok.RequiredArgsConstructor;

/**
 * @author User
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
	
	private final IUserRepository<User> userRepository;

	@Override
	public UserDTO createUser(User user) {
		return UserDTOMapper.fromUser(userRepository.create(user));
	}

}
