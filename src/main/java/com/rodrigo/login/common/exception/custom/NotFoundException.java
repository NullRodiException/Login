package com.rodrigo.login.common.exception.custom;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{
    public NotFoundException(String error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
