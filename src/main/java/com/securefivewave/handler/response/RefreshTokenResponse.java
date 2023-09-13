package com.securefivewave.handler.response;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RefreshTokenResponse {
	private String accessToken;
	private String refreshToken;
	private Date accessTokenExpiryDate;
	private Date refreshTokenExpiryDate;
	private boolean success;
	private String message;
	private Integer errorCode;
}
