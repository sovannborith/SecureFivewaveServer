package com.securefivewave.auth;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpResponse {
	private String result;
}
