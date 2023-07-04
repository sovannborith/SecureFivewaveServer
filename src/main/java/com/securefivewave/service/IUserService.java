/**
 * 
 */
package com.securefivewave.service;


import java.util.List;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;

public interface IUserService {
	public UserDTO createUser(User user) throws Exception;
	public UserDTO getUserByEmail(String email);
	public List<UserDTO> getAllUsers();
	public User update(User user);
	public boolean enableUser(Long userId);
}
