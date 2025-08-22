package com.rodrigo.login.contract.user;

import com.rodrigo.login.contract.exception.response.ExceptionResponse;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.contract.user.response.GetAllUsersResponse;
import com.rodrigo.login.contract.user.response.PostUserResponse;
import com.rodrigo.login.contract.user.response.UserResponse;
import com.rodrigo.login.implementation.services.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Builder
@Tag(name = "Users", description = "Operations for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Users", description = "Returns all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = GetAllUsersResponse.class))),
    })
    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Post User", description = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = PostUserResponse.class))),
    })
    @PostMapping
    public ResponseEntity<PostUserResponse> postUser(@Valid @RequestBody PostUserRequest payload) {
        return userService.postUser(payload);
    }

    @Operation(summary = "Get User by ID", description = "Get user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Delete User", description = "Delete user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Patch User", description = "Patch user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchUser(@PathVariable("id") String id,@Valid @RequestBody PatchUserRequest payload) {
        return userService.patchUser(id, payload);
    }
}
