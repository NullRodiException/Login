package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.common.exception.custom.NotFoundException;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
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
public class PatchUserByIdService {
    private final UserRepository repository;
    private final ValidateUser validateUser;

    public ResponseEntity<Void> patchUser(String id, PatchUserRequest payload){
        validateUser.validate(payload);

        User user = repository.findById(UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );

        user.setName(payload.name().orElse(user.getName()));
        user.setUsername(payload.username().orElse(user.getUsername()));
        user.setEmail(payload.email().orElse(user.getEmail()));
        user.setHashedPassword(payload.hashedPassword().orElse(user.getHashedPassword()));

        repository.save(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
