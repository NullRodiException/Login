package com.rodrigo.login.implementation.services.user;

import com.rodrigo.login.contract.user.response.GetAllUsersResponse;
import com.rodrigo.login.contract.user.response.UserResponse;
import com.rodrigo.login.implementation.model.User;
import com.rodrigo.login.implementation.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@Builder
public class GetAllUsersService {
    private final UserRepository repository;

    public ResponseEntity<GetAllUsersResponse> getAllUsers() {
        Iterable<User> users = repository.findAll();
        GetAllUsersResponse response = new GetAllUsersResponse(new ArrayList<>());
        users.forEach(user -> response.users().add(new UserResponse(
                user.getName(),
                user.getUsername(),
                user.getEmail()
        )));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
