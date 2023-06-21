/**
 * 
 */
package com.securefivewave.dto;

import java.time.LocalDateTime;

import lombok.Data;
/**
 * @author User
 *
 */
@Data
public class UserDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String bio;
	private String imgUrl;
	private Boolean isEnable;
	private Boolean isLocked;
	private Boolean isMfa;
	private LocalDateTime createdAt;
}
