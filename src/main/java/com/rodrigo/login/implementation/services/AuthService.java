package com.rodrigo.login.implementation.services;

import com.rodrigo.login.common.exception.custom.InvalidLoginException;
import com.rodrigo.login.common.security.HashPassword;
import com.rodrigo.login.contract.auth.request.ChangePasswordRequest;
import com.rodrigo.login.contract.auth.request.LoginRequest;
import com.rodrigo.login.implementation.entity.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private final UserRepository repository;
    private final HashPassword hashPassword;
    private final MessageService messageService;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthService(UserRepository repository, HashPassword hashPassword, MessageService messageService, UserService userService, TokenService tokenService) {
        this.repository = repository;
        this.hashPassword = hashPassword;
        this.messageService = messageService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public String postLogin(LoginRequest payload){
        User user = repository.findByLogin(payload.login())
                .orElseThrow(() -> new InvalidLoginException(
                        messageService.getMessage("user.not.found")
                ));

        if (!hashPassword.verifyPassword(payload.password(), user.getHashedPassword())) {
            throw new InvalidLoginException(
                    messageService.getMessage("error.invalid.credentials")
            );
        }
        return tokenService.generateToken(user);
    }

    public void changePassword(String id, ChangePasswordRequest payload){
        User user = userService.findUserById(id);
        if(!hashPassword.verifyPassword(payload.password(), user.getHashedPassword())){
            throw new InvalidLoginException(
                    messageService.getMessage("error.invalid.password")
            );
        }

        user.setHashedPassword(hashPassword.hashPassword(payload.newPassword()));
        user.setUpdatedAt(java.time.LocalDateTime.now());
        repository.save(user);
    }
}
