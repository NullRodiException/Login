package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.common.exception.custom.NotFoundException;
import com.rodrigo.login.contract.user.response.UserResponse;
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
public class GetUserByIdService {
    private final UserRepository repository;

    public ResponseEntity<UserResponse> getUserById(String id){
        User user = repository.findById(UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
        UserResponse response = new UserResponse(
                user.getName(),
                user.getUsername(),
                user.getEmail()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
