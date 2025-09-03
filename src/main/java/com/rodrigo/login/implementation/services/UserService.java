package com.rodrigo.login.implementation.services;

import com.rodrigo.login.common.enums.UserRoleEnum;
import com.rodrigo.login.common.exception.custom.InvalidPromotionException;
import com.rodrigo.login.common.exception.custom.InvalidRequestException;
import com.rodrigo.login.common.exception.custom.NotFoundException;
import com.rodrigo.login.common.security.HashPassword;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.contract.user.response.GetAllUsersResponse;
import com.rodrigo.login.contract.user.response.PostUserResponse;
import com.rodrigo.login.contract.user.response.UserResponse;
import com.rodrigo.login.implementation.entity.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final HashPassword hashPassword;
    private final MessageService messageService;

    public UserService(UserRepository repository, HashPassword hashPassword, MessageService messageService) {
        this.repository = repository;
        this.hashPassword = hashPassword;
        this.messageService = messageService;
    }

    public ResponseEntity<GetAllUsersResponse> getUsers() {
        Iterable<User> users = repository.findAll();
        GetAllUsersResponse response = new GetAllUsersResponse(new ArrayList<>());
        users.forEach(user -> response.users().add(buildUserResponse(user)));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<PostUserResponse> postUser(PostUserRequest payload) {
        this.validate(payload);
        String hashedPassword = hashPassword.hashPassword(payload.password());

        User user = new User();
        user.setName(payload.name());
        user.setUsername(payload.username());
        user.setEmail(payload.email());
        user.setHashedPassword(hashedPassword);
        user.setRole(UserRoleEnum.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        repository.save(user);

        PostUserResponse response = new PostUserResponse(user.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public ResponseEntity<UserResponse> getUserById(String id){
        User user = this.findUserById(id);
        UserResponse response = buildUserResponse(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<Void> deleteUser(String id){
        User user = this.findUserById(id);

        repository.removeUserById(user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Void> patchUser(String id, PatchUserRequest payload){
        this.validate(UUID.fromString(id), payload);
        User user = this.findUserById(id);

        payload.name()
                .filter(StringUtils::hasText)
                .ifPresent(user::setName);
        payload.username()
                .filter(StringUtils::hasText)
                .ifPresent(user::setUsername);
        payload.email()
                .filter(StringUtils::hasText)
                .ifPresent(user::setEmail);

        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Void> promoteUserToAdmin(String id){
        User user = this.findUserById(id);
        if(user.getRole() == UserRoleEnum.ADMIN){
            throw new InvalidPromotionException(messageService.getMessage("error.user.has.admin"));
        }
        user.setRole(UserRoleEnum.ADMIN);
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public User findUserById(String id){
        try {
            return repository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new NotFoundException(messageService.getMessage("user.not.found"))
            );
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(messageService.getMessage("user.not.found"));
        }
    }

    private UserResponse buildUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private void validate(PostUserRequest payload){
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

    private void validate(UUID id, PatchUserRequest payload) {
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
