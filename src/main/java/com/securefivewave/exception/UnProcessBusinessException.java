package com.securefivewave.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Unprocessed with your request"
)
public class UnProcessBusinessException extends BusinessException {
    public UnProcessBusinessException() {
    }

    public UnProcessBusinessException(String message) {
        super(message);
    }

    public UnProcessBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnProcessBusinessException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }

    public UnProcessBusinessException(String msg, Integer errorCode, Throwable cause) {
        super(msg, errorCode, cause);
    }
}