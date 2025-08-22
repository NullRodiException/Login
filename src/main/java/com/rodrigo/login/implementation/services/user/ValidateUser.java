package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.common.utils.Helpers;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.implementation.repository.UserRepository;
import com.rodrigo.login.implementation.services.app.MessageService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@Builder
public class ValidateUser {
    private final UserRepository repository;
    private final Helpers helpers;
    private final MessageService messageService;

    public void validate(PostUserRequest payload){
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add(messageService.getMessage("request.payload.empty"));
            helpers.throwIfErrors(errors);
            return;
        }

        if (!helpers.isBlank(payload.email()) && repository.existsByEmail(payload.email())) {
            errors.add(messageService.getMessage("user.email.duplicate"));
        }
        if (!helpers.isBlank(payload.username()) && repository.existsByUsername(payload.username())) {
            errors.add(messageService.getMessage("user.username.duplicate"));
        }
        helpers.throwIfErrors(errors);
    }

    public void validate(UUID id, PatchUserRequest payload) {
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add(messageService.getMessage("request.payload.empty"));
            helpers.throwIfErrors(errors);
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

        helpers.throwIfErrors(errors);
    }
}
