/**
 * 
 */
package com.securefivewave.service.user;


import java.util.List;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;

public interface IUserService {
	public User createUser(User user) throws Exception;
	public User getUserByEmail(String email);
	public List<UserDTO> getAllUsers();
	public User update(User user);
	public boolean enableUser(Long userId);
}
