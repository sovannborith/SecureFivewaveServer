package com.securefivewave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
	private Long id;
	private Long userId;
	private Long roleId;
	private Boolean isActive;
}
