package com.securefivewave.exception;

@SuppressWarnings("serial")
public class ApiException extends RuntimeException{
	public ApiException(String message) {
		super(message);
	}
}
