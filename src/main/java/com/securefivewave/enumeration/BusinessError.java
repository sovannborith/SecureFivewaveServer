package com.securefivewave.enumeration;

import com.securefivewave.exception.AuthorizationRequiredBusinessException;
import com.securefivewave.exception.BusinessException;
import com.securefivewave.exception.InternalBusinessException;

import lombok.Getter;


@Getter
public enum BusinessError {

    CODE_VERIFICATION_INVALID(new InternalBusinessException("OTP service is temporary unavailable, please try again", 1001)),
    ACCESS_DENIED(new AuthorizationRequiredBusinessException("Unauthorized User", 1002));
    private final BusinessException businessException;
    private BusinessError(BusinessException businessException) {
        this.businessException = businessException;
    }

    public BusinessException throwException() {
        return  businessException;
    }
}