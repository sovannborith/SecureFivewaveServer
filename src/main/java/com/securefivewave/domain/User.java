package com.securefivewave.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_DEFAULT)
@Data
public class User {
	
	private Long id;
	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	@NotEmpty(message = "Last name cannot be empty")
	private String lastName;
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Invalid email! Please enter a valid email address")
	private String email;
	@NotEmpty(message = "Password cannot be empty")
	private String password;
	private String phone;
	private String bio;
	private String imgUrl;
	private Boolean isEnable;
	private Boolean isLocked;
	private Boolean isMfa;
	private LocalDateTime createdAt;
}
