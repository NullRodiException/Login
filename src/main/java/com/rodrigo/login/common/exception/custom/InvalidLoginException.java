package com.rodrigo.login.common.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends BaseException{
    public InvalidLoginException(String error) {
        super(error, HttpStatus.UNAUTHORIZED);
    }
}
