package com.securefivewave.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(
        value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "An internal error has happened."
)
public final class InternalBusinessException extends BusinessException {
    public InternalBusinessException() {
    }

    public InternalBusinessException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }

    public InternalBusinessException(String msg, Integer errorCode, Throwable cause) {
        super(msg, errorCode, cause);
    }

    public InternalBusinessException(String message) {
        super(message);
    }

    public InternalBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalBusinessException(Throwable cause) {
        super(cause);
    }

}