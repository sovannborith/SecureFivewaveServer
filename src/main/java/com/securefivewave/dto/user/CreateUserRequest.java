/**
 * 
 */
package com.securefivewave.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author User
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	private String firstName;
	private String lastName;
	private String email;
}
