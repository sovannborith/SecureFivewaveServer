package com.securefivewave.dto.permission;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class PermissionResponse{

	private PermissionRequest permissionRequest;
	private boolean success;
	private String message;
	private Integer errorCode;
}