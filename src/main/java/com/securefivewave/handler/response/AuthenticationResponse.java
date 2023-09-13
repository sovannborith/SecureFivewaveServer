package com.securefivewave.handler.response;

import java.util.Date;
import com.securefivewave.handler.request.AuthenticationRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

	private Long id;
	private String email;
	private String accessToken;
	private Date accessTokenExpiryDate;
	private Date refreshTokenExpiryDate;
	private String refreshToken;
	private boolean success;
	private String message;
	private Integer errorCode;
	private AuthenticationRequest authRequest;
}
