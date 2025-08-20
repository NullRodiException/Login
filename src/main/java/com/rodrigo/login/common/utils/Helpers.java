package com.rodrigo.login.common.utils;

import com.rodrigo.login.common.exception.custom.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Helpers {

    public boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }

    public void throwIfErrors(List<String> errors) {
        if (!errors.isEmpty()) {
            throw new InvalidRequestException(errors);
        }
    }
}
