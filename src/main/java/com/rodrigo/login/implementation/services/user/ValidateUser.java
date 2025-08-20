package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.common.utils.Helpers;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Builder
public class ValidateUser {
    private final UserRepository repository;
    private final Helpers helpers;

    public void validate(PostUserRequest payload){
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add("Request Payload is Empty");
            helpers.throwIfErrors(errors);
            return;
        }

        if (helpers.isBlank(payload.name())) errors.add("Name is Empty");
        if (helpers.isBlank(payload.username())) errors.add("Username is Blank");
        if (helpers.isBlank(payload.email())) errors.add("Email is Blank");
        if (helpers.isBlank(payload.password())) errors.add("Password is Blank");

        if (!helpers.isBlank(payload.email()) && repository.existsByEmail(payload.email())) {
            errors.add("Email already exists");
        }
        if (!helpers.isBlank(payload.username()) && repository.existsByUsername(payload.username())) {
            errors.add("Username already exists");
        }
        helpers.throwIfErrors(errors);
    }

    public void validate(PatchUserRequest payload) {
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add("Request Payload is Empty");
            helpers.throwIfErrors(errors);
            return;
        }

        payload.name().ifPresent(v -> {
            if (helpers.isBlank(v)) errors.add("Name is Blank");
        });
        payload.username().ifPresent(v -> {
            if (helpers.isBlank(v)) errors.add("Username is Blank");
            else if (repository.existsByUsername(v)) errors.add("Username already exists");
        });
        payload.email().ifPresent(v -> {
            if (helpers.isBlank(v)) errors.add("Email is Blank");
            else if (repository.existsByEmail(v)) errors.add("Email already exists");
        });
        payload.hashedPassword().ifPresent(v -> {
            if (helpers.isBlank(v)) errors.add("Password is Blank");
        });

        if (payload.name().isEmpty()
                && payload.username().isEmpty()
                && payload.email().isEmpty()
                && payload.hashedPassword().isEmpty()) {
            errors.add("At least one field must be provided to patch");
        }

        helpers.throwIfErrors(errors);
    }
}
