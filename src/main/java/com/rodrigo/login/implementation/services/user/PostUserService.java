package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.common.utils.HashPassword;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.contract.user.response.PostUserResponse;
import com.rodrigo.login.implementation.model.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@Builder
public class PostUserService {
    private final UserRepository repository;
    private final ValidateUser validateUser;
    private final HashPassword hashPassword;

    public ResponseEntity<PostUserResponse> postUser(PostUserRequest payload) {
        validateUser.validate(payload);
        String hashedPassword = hashPassword.hashPassword(payload.password());

        User user = new User();
        user.setName(payload.name());
        user.setUsername(payload.username());
        user.setEmail(payload.email());
        user.setHashedPassword(hashedPassword);

        repository.save(user);

        PostUserResponse response = new PostUserResponse(UUID.randomUUID());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
