package com.rodrigo.login.implementation.services.app;

import com.rodrigo.login.common.exception.custom.NotFoundException;
import com.rodrigo.login.implementation.model.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Builder
@Slf4j
public class UserHelper {
    private final UserRepository repository;
    private final MessageService messageService;

    public User getUser(String id){
        return repository.findById(UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException(messageService.getMessage("user.not.found"))
        );
    }
}
