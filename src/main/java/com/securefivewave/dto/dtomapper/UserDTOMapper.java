package com.securefivewave.dto.dtomapper;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;

@Component
public class UserDTOMapper {
	public static UserDTO fromUser(User user) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		
		return userDTO;
	}

	public static Optional<UserDTO> fromUser(Optional<User> user) {
		if(user.isPresent()) return fromUser(user);
		return null;
	}
	
	public static User toUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO,user);
		
		return user;
	}

	public static Optional<User> toUser(Optional<UserDTO> userDTO) {
		if(userDTO.isPresent()) return toUser(userDTO);
		return null;
	}
}
