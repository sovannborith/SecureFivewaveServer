package com.securefivewave.handler;
import com.securefivewave.config.AppConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securefivewave.exception.BusinessException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommonResponse<T> {

    private boolean success;

    @JsonProperty("error_code")
    private Integer errorCode;

    private String message;

    private T data;

    private String tracer;

    public CommonResponse(boolean success, Integer errorCode, String message, T data) {
        this(success, errorCode, message, data, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> CommonResponse successResponse(T data) {
        return new CommonResponse(true, null, null, data);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> CommonResponse<T> successResponseForStaff(CommonResponse<T> commonResponse) {
        return new CommonResponse(true, null, commonResponse.getMessage(), commonResponse.getData());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static CommonResponse errorResponse(Integer errorCode, String errorMessage) {
        return new CommonResponse(false, errorCode, errorMessage, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> CommonResponse<T> errorResponse(Integer errorCode, String errorMessage, Throwable error) {

        return new CommonResponse(false, errorCode, errorMessage, null, getTracer(error));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static CommonResponse errorResponse(BusinessException exception) {
        return new CommonResponse(false, exception.getErrorCode(), exception.getMessage(), null, getTracer(exception));
    }

    private static String getTracer(Throwable ex) {
        return AppConfig.isTracerEnabled() ? ExceptionUtils.getStackTrace(ex) : null;
    }

}