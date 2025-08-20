package com.rodrigo.login.implementation.services.login;

import com.rodrigo.login.common.utils.Helpers;
import com.rodrigo.login.contract.login.request.LoginRequest;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Builder
public class ValidateLogin {
    private final UserRepository repository;
    private final Helpers helpers;

    public void validate(LoginRequest payload){
        List<String> errors = new ArrayList<>();
        if (payload == null) {
            errors.add("Request Payload is Empty");
            helpers.throwIfErrors(errors);
            return;
        }

        if (helpers.isBlank(payload.login())) errors.add("Username is Blank");
        if (helpers.isBlank(payload.password())) errors.add("Password is Blank");
        helpers.throwIfErrors(errors);
    }
}
