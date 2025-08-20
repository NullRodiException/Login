package com.rodrigo.login.contract.exception.response;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionResponse(
        LocalDateTime timestamp,
        List<String> errors
) {
    public ExceptionResponse(List<String> errors) {
        this(LocalDateTime.now(), errors);
    }
}
