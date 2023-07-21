package com.securefivewave.handler.request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpRequest {
	private String email;
	private String otp;
}
