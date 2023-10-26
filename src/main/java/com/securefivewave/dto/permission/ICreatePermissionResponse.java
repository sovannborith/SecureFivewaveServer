package com.securefivewave.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ICreatePermissionResponse {
    private ICreatePermissionRequest permissionRequest;
    private boolean success;
	private String message;
	private Integer errorCode;

}