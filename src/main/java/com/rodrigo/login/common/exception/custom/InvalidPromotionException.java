package com.rodrigo.login.common.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidPromotionException extends BaseException{
    public InvalidPromotionException(String error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
