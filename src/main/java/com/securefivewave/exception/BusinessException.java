package com.securefivewave.exception;

@SuppressWarnings("serial")
public abstract class BusinessException extends RuntimeException {
    private Integer errorCode;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String msg, Integer errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String msg, Integer errorCode, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
