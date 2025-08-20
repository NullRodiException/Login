package com.rodrigo.login.common.exception.custom;

import org.springframework.http.HttpStatus;

import java.util.List;

public class InvalidRequestException extends BaseException{
    public InvalidRequestException(List<String> errors) {
        super(errors, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequestException(String error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
