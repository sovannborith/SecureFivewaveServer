package com.securefivewave.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(
        value = HttpStatus.FORBIDDEN,
        reason = "You are not allowed to access this URI"
)
public final class AuthorizationRequiredBusinessException extends BusinessException {
    public AuthorizationRequiredBusinessException() {
    }

    public AuthorizationRequiredBusinessException(String message) {
        super(message);
    }

    public AuthorizationRequiredBusinessException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }

    public AuthorizationRequiredBusinessException(String message, Throwable cause) {
        this(message, (Integer)null, cause);
    }

    public AuthorizationRequiredBusinessException(String msg, Integer errorCode, Throwable cause) {
        super(msg, errorCode, cause);
    }

    public AuthorizationRequiredBusinessException(Throwable cause) {
        super(cause);
    }

}