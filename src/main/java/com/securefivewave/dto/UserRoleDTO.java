package com.securefivewave.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
	private Long id;
	private Long userId;
	private Long roleId;
	private Boolean isActive;
}
