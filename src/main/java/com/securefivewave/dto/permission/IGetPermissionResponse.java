package com.securefivewave.dto.permission;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class IGetPermissionResponse{

	private IPermission permission;
	private boolean success;
	private String message;
	private Integer errorCode;
}