package com.securefivewave.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class UserRole {
	private Long id;
	@NotEmpty(message = "Role ID cannot be empty")
	private Long roleId;
	@NotEmpty(message = "User ID cannot be empty")
	private Long userId;
	private Boolean isActive;
}
