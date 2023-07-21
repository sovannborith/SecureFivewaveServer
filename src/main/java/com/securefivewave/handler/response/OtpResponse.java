package com.securefivewave.handler.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OtpResponse {
	private String email;
	private String otp;
}
