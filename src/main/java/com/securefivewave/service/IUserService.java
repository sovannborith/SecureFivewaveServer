/**
 * 
 */
package com.securefivewave.service;


import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;

public interface IUserService {
	public UserDTO createUser(User user);
	public UserDTO getUserByEmail(String email);
}
