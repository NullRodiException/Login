package com.rodrigo.login.implementation.services;

import com.rodrigo.login.common.exception.custom.InvalidRequestException;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Builder
public class UserValidator {
    private final UserRepository repository;
    private final MessageService messageService;

    public void validate(PostUserRequest payload){
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add(messageService.getMessage("request.payload.empty"));
            this.throwIfErrors(errors);
            return;
        }

        if (this.isNotBlank(payload.email()) && repository.existsByEmail(payload.email())) {
            errors.add(messageService.getMessage("user.email.duplicate"));
        }
        if (this.isNotBlank(payload.username()) && repository.existsByUsername(payload.username())) {
            errors.add(messageService.getMessage("user.username.duplicate"));
        }
        this.throwIfErrors(errors);
    }

    public void validate(UUID id, PatchUserRequest payload) {
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add(messageService.getMessage("request.payload.empty"));
            this.throwIfErrors(errors);
            return;
        }

        payload.email().ifPresent(email -> {
            if (repository.existsByEmailAndIdNot(email, id)) {
                errors.add(messageService.getMessage("user.email.duplicate"));
            }
        });
        payload.username().ifPresent(username -> {
            if (repository.existsByUsernameAndIdNot(username, id)) {
                errors.add(messageService.getMessage("user.username.duplicate"));
            }
        });

        this.throwIfErrors(errors);
    }

    private boolean isNotBlank(String v) {
        return v != null && !v.trim().isEmpty();
    }

    private void throwIfErrors(List<String> errors) {
        if (!errors.isEmpty()) {
            throw new InvalidRequestException(errors);
        }
    }

}
