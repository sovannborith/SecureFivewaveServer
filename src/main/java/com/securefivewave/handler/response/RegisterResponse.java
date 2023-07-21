package com.securefivewave.handler.response;
import com.securefivewave.handler.request.RegisterRequest;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RegisterResponse{

	private RegisterRequest registerRequest;
	private boolean success;
	private String message;
	private Integer errorCode;
}
