package com.securefivewave.handler.response;

import com.fasterxml.jackson.annotation.JsonProperty;

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
   
	public static <T> CommonResponse<T> successResponse(T data) {
        return new CommonResponse<T>(true, null, null, data);
    }
    public static <T> CommonResponse<T> successResponse(String message, T data) {
        return new CommonResponse<T>(true, null, message, data);
    }

    public static <T> CommonResponse<T> successResponse(Integer errorCode,String message, T data) {
        return new CommonResponse<T>(true, errorCode, message, data);
    }

    public static <T> CommonResponse<T> successResponse(boolean success,Integer errorCode,String message, T data) {
        return new CommonResponse<T>(success, errorCode, message, data);
    }

	public static <T> CommonResponse<T> errorResponse(Integer errorCode, String errorMessage) {
        return new CommonResponse<T>(false, errorCode, errorMessage, null);
    }
}