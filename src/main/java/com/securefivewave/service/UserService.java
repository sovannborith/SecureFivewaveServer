/**
 * 
 */
package com.securefivewave.service;

import com.securefivewave.domain.User;
import com.securefivewave.dto.UserDTO;

/**
 * @author User
 *
 */
public interface UserService {
	public UserDTO createUser(User user);
}
