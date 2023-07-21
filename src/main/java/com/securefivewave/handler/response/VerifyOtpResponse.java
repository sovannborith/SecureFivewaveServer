package com.securefivewave.handler.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpResponse {
	private OtpResponse otpResponse;
	private boolean success;
	private Integer errorCode;
	private String message;
}
