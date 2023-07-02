package com.securefivewave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDTO {
	private Long id;
	private Long userId;
	private String accessToken;
	private String tokenType;
	private Boolean isRevoked;
	private Boolean isExpired;
}
