package com.rodrigo.login.contract.user;

import com.rodrigo.login.contract.exception.response.ExceptionResponse;
import com.rodrigo.login.contract.user.request.PatchUserRequest;
import com.rodrigo.login.contract.user.request.PostUserRequest;
import com.rodrigo.login.contract.user.response.GetAllUsersResponse;
import com.rodrigo.login.contract.user.response.PostUserResponse;
import com.rodrigo.login.contract.user.response.UserResponse;
import com.rodrigo.login.implementation.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operations for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get Users", description = "Returns all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = GetAllUsersResponse.class))),
    })
    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getUsers() {
        GetAllUsersResponse response = userService.getUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "Post User", description = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = PostUserResponse.class))),
    })
    @PostMapping
    public ResponseEntity<PostUserResponse> postUser(@Valid @RequestBody PostUserRequest payload) {
        PostUserResponse response = userService.postUser(payload);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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
        UserResponse response = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "Delete User", description = "Delete user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Patch User", description = "Patch user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchUser(@PathVariable("id") String id,@Valid @RequestBody PatchUserRequest payload) {
        userService.patchUser(id, payload);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Promote User", description = "Promote user to Admin by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/promote/{id}")
    public ResponseEntity<Void> promoteUserToAdmin(@PathVariable("id") String id){
        userService.promoteUserToAdmin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
