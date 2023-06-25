package com.securefivewave.handler;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.securefivewave.exception.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ResponseHandler {

    private ResponseHandler() {
        // nothing
    }

    @SuppressWarnings("unchecked")
	public static <U> ResponseEntity<CommonResponse<U>> buildSuccessResponse(U response) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successResponse(response));
    }

    @SuppressWarnings("rawtypes")
	public static ResponseEntity<CommonResponse> buildEmptySuccessResponse() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(null);
    }
    /*
    @SuppressWarnings("unchecked")
	public static <U> ResponseEntity<CommonResponse<U>> buildSuccessResponseForStaff(U response) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successResponseForStaff((CommonResponse) response));
    }
    */

    @SuppressWarnings("unchecked")
	public static <T> ResponseEntity<CommonResponse<T>> buildErrorResponse(Throwable error) {
        return buildErrorResponseEntity(error);
    }
    
    @SuppressWarnings("rawtypes")
	private static ResponseEntity buildErrorResponseEntity(Throwable error) {
        log.error("Response Error: {}", error.getMessage(), error);
        if (error instanceof UnProcessBusinessException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(422, error.getMessage(), error));
        }
        else if (error instanceof WebClientResponseException.Conflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(409, "User Already Created "));
        }
        else if (error instanceof WebClientResponseException.NotFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(404, "Not Found", error));

        }
        else if (error instanceof WebClientResponseException.BadRequest) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(400, "Invalid referral information", error));

        }
        else if (error instanceof WebClientResponseException.Forbidden) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(403, "Access denied", error));
        }
        else if (error instanceof WebClientResponseException.TooManyRequests) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(429, "Too Many Requests – You’re requesting too many resources!",
                            error));
        }
        else if (error instanceof WebClientResponseException.UnprocessableEntity) {
            String errorHttpStatus;
            Integer errorSubCode;
            String errorMessage;

            WebClientResponseException.UnprocessableEntity unprocessableEntity = (WebClientResponseException.UnprocessableEntity) error;
            ObjectMapper jsonMapper = new ObjectMapper();
            try {
                log.error("response body: {}", unprocessableEntity.getResponseBodyAsString());
                CommonErrorResponse errorResponse = jsonMapper.readValue(unprocessableEntity.getResponseBodyAsString(),
                        CommonErrorResponse.class);
                errorHttpStatus = HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase();
                errorSubCode = errorResponse.getErrorCode();
                errorMessage = errorResponse.getMessage();
            }
            catch (Exception ex) {
                errorHttpStatus = HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase();
                errorSubCode = null;
                errorMessage = unprocessableEntity.getMessage();
            }

            log.error("Error response: {} - {} - {} ", errorHttpStatus, errorSubCode, errorMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(errorSubCode, errorMessage, error));

        }
        else if (error instanceof InvalidParameterBusinessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse((InvalidParameterBusinessException) error));
        }
        else if (error instanceof NotFoundBusinessException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse((NotFoundBusinessException) error));
        }
        else if (error instanceof AuthenticationBusinessException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse((AuthenticationBusinessException) error));
        }
        else if (error instanceof AuthorizationRequiredBusinessException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse((AuthorizationRequiredBusinessException) error));
        }
        else if (error instanceof InternalBusinessException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse((InternalBusinessException) error));
        }
        else if (error instanceof InvalidCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(401, error.getMessage(), error));
        }
        else if (error instanceof DuplicateKeyException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(409, error.getMessage(), error));
        }
        else if (error instanceof UsernameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(404, error.getMessage(), error));
        }
        else if (error instanceof BusinessException) { // business error will return 422
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse((BusinessException) error));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.errorResponse(500, error.getMessage(), error));
    }
    
    @SuppressWarnings("unchecked")
	public static <T> ResponseEntity<CommonResponse<T>> buildCustomErrorResponse(Throwable error, Integer customCode) {
        log.error("Response Error: {}", error.getMessage(), error);
        if (error instanceof InvalidCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(customCode, error.getMessage()));
        }
        else if (error instanceof UnProcessBusinessException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(customCode, error.getMessage()));
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                    .body(CommonResponse.errorResponse(500, error.getMessage()));
        }
    }

}