package com.rodrigo.login.implementation.services.app;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
@Builder
public class MessageService {
    private final MessageSource messageSource;

    public String getMessage(String key, Object... args){
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
