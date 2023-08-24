/**
 * 
 */
package com.securefivewave.dto.user;

import java.util.Date;

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
public class ChangeUserProfileResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String bio;
	private String imgUrl;
	private String phone;
	private boolean isMfa;
	private boolean isEnabled;
	private boolean isLocked;
	private Date createdAt;
}
