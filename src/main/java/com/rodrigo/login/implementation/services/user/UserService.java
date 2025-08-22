package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.common.exception.custom.NotFoundException;
import com.rodrigo.login.common.utils.HashPassword;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.contract.user.response.GetAllUsersResponse;
import com.rodrigo.login.contract.user.response.PostUserResponse;
import com.rodrigo.login.contract.user.response.UserResponse;
import com.rodrigo.login.implementation.model.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Slf4j
@Builder
public class UserService {
    private final UserRepository repository;
    private final ValidateUser validateUser;
    private final HashPassword hashPassword;

    public ResponseEntity<GetAllUsersResponse> getUsers() {
        Iterable<User> users = repository.findAll();
        GetAllUsersResponse response = new GetAllUsersResponse(new ArrayList<>());
        users.forEach(user -> response.users().add(new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail()
        )));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<PostUserResponse> postUser(PostUserRequest payload) {
        validateUser.validate(payload);
        String hashedPassword = hashPassword.hashPassword(payload.password());

        User user = new User();
        user.setName(payload.name());
        user.setUsername(payload.username());
        user.setEmail(payload.email());
        user.setHashedPassword(hashedPassword);

        repository.save(user);

        PostUserResponse response = new PostUserResponse(user.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public ResponseEntity<UserResponse> getUserById(String id){
        User user = repository.findById(UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );
        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<Void> deleteUser(String id){

        User user = repository.findById(UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );

        repository.removeUserById(user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Void> patchUser(String id, PatchUserRequest payload){
        validateUser.validate(UUID.fromString(id), payload);
        User user = repository.findById(UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException("User Not Found")
        );

        payload.name()
                .filter(StringUtils::hasText)
                .ifPresent(user::setName);
        payload.username()
                .filter(StringUtils::hasText)
                .ifPresent(user::setUsername);
        payload.email()
                .filter(StringUtils::hasText)
                .ifPresent(user::setEmail);

        repository.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
