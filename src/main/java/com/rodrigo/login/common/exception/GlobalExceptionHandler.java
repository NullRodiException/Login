package com.rodrigo.login.common.exception;

import com.rodrigo.login.common.exception.custom.BaseException;
import com.rodrigo.login.contract.exception.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleShippingBaseException(BaseException ex){
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ExceptionResponse(ex.getErrors()));
    }
}
