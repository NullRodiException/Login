package com.rodrigo.login.implementation.services.login;

import com.rodrigo.login.common.exception.custom.InvalidLoginException;
import com.rodrigo.login.common.utils.HashPassword;
import com.rodrigo.login.contract.login.request.LoginRequest;
import com.rodrigo.login.contract.login.response.LoginResponse;
import com.rodrigo.login.implementation.model.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Builder
public class AuthService {
    private final UserRepository repository;
    private final HashPassword hashPassword;

    public ResponseEntity<LoginResponse> postLogin(LoginRequest payload){
        User user = repository.findByLogin(payload.login())
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));

        if (!hashPassword.verifyPassword(payload.password(), user.getHashedPassword())) {
            throw new InvalidLoginException("Invalid username or password");
        }

        LoginResponse response = new LoginResponse(user.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
