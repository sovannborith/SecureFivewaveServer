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
public class ChangeProfileRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String bio;
	private String imgUrl;
	private String phone;
	private boolean isMfa;
}
