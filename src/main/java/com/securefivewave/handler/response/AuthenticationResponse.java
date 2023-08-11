package com.securefivewave.handler.response;

import java.util.Date;
import com.securefivewave.handler.request.AuthenticationRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

	//@JsonProperty("access_token")
	private String accessToken;
	private Date jwtTokenExpiryDate;
	//@JsonProperty("refresh_token")
	private String refreshToken;
	private boolean success;
	private String message;
	private Integer errorCode;
	private AuthenticationRequest authRequest;
}
