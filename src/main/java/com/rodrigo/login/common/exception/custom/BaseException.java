package com.rodrigo.login.common.exception.custom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public abstract class BaseException extends RuntimeException{
    private final List<String> errors;
    private final HttpStatus httpStatus;

    public BaseException(List<String> errors, HttpStatus httpStatus) {
        super(errors.getFirst());
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public BaseException(String error, HttpStatus httpStatus) {
        super(error);
        this.errors = List.of(error);
        this.httpStatus = httpStatus;
    }
}
