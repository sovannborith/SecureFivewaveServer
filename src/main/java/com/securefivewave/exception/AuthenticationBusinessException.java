package com.securefivewave.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(
        value = HttpStatus.UNAUTHORIZED,
        reason = "Your request is missing authentication details or they are incorrect."
)
public class AuthenticationBusinessException extends BusinessException {
    public AuthenticationBusinessException() {
    }

    public AuthenticationBusinessException(String message) {
        super(message);
    }

    public AuthenticationBusinessException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }

    public AuthenticationBusinessException(String message, Throwable cause) {
        this(message, (Integer)null, cause);
    }

    public AuthenticationBusinessException(String msg, Integer errorCode, Throwable cause) {
        super(msg, errorCode, cause);
    }

    public AuthenticationBusinessException(Throwable cause) {
        super(cause);
    }

}