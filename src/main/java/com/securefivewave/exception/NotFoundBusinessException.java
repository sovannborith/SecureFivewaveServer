package com.securefivewave.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = "The specified object was not found."
)
public class NotFoundBusinessException extends BusinessException {
    public NotFoundBusinessException() {
    }

    public NotFoundBusinessException(String message) {
        super(message);
    }

    public NotFoundBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundBusinessException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }


}