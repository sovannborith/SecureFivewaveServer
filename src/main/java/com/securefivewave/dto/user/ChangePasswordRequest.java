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
public class ChangePasswordRequest {
	private String email;
	private String oldPassword;
	private String newPassword;
}
