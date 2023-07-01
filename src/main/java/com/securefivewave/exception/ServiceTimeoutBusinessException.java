package com.securefivewave.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.GATEWAY_TIMEOUT,
        reason = "Gateway Timeout calling service."
)
public class ServiceTimeoutBusinessException extends BusinessException {
    public ServiceTimeoutBusinessException() {
    }

    public ServiceTimeoutBusinessException(String message) {
        super(message);
    }

    public ServiceTimeoutBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceTimeoutBusinessException(Throwable cause) {
        super(cause);
    }

}