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
public class ChangePasswordResponse {
	private String email;
	private String newPassword;
}
