package com.securefivewave.handler.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securefivewave.handler.request.AuthenticationRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("refresh_token")
	private String refreshToken;
	private boolean success;
	private String message;
	private Integer errorCode;
	private AuthenticationRequest authRequest;
}
