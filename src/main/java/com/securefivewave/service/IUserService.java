/**
 * 
 */
package com.securefivewave.service;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;

/**
 * @author User
 *
 */
public interface IUserService {
	public UserDTO createUser(User user);
}
