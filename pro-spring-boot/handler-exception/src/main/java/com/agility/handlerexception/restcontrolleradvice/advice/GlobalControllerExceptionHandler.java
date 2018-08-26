package com.agility.handlerexception.restcontrolleradvice.advice;

import com.agility.handlerexception.restcontrolleradvice.exception.BaseCustomException;
import com.agility.handlerexception.restcontrolleradvice.model.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BaseCustomException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getCode(),
            ex.getLocalizedMessage());
        ResponseEntity responseEntity = new ResponseEntity(apiErrorResponse, ex.getHttpStatus());
        return responseEntity;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.ApiErrorResponseBuilder.anApiErrorResponse()
            .withStatus(status)
            .withCode(status.BAD_REQUEST.value())
            .withMessage(ex.getBindingResult().toString()).build();

        logger.info("getAllErrors");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            logger.info("handleMethodArgumentNotValid: {} {}", error.getObjectName(), error.getDefaultMessage());
        });
        // handleMethodArgumentNotValid: customer must be less than or equal to 20
        // handleMethodArgumentNotValid: customer size must be between 1 and 60

        logger.info("getFieldErrors");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            logger.info("handleMethodArgumentNotValid: {} {}", error.getField(), error.getDefaultMessage());
        });
        // handleMethodArgumentNotValid: age must be less than or equal to 20
        // handleMethodArgumentNotValid: name size must be between 1 and 60

        logger.info("getGlobalErrors");
        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            logger.info("handleMethodArgumentNotValid: {} {}", error.getObjectName(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.ApiErrorResponseBuilder.anApiErrorResponse()
            .withStatus(status)
            .withCode(status.value())
            .withMessage(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Default exception handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> defaultExceptionHandler(Exception ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
