package com.securefivewave.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "Incorrect parameters were provided."
)
public class InvalidParameterBusinessException extends BusinessException {
    public InvalidParameterBusinessException() {
    }

    public InvalidParameterBusinessException(String message) {
        super(message);
    }

    public InvalidParameterBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterBusinessException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }

    public InvalidParameterBusinessException(String msg, Integer errorCode, Throwable cause) {
        super(msg, errorCode, cause);
    }
}