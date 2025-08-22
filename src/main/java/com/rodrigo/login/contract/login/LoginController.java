package com.rodrigo.login.contract.login;

import com.rodrigo.login.contract.exception.response.ExceptionResponse;
import com.rodrigo.login.contract.login.request.LoginRequest;
import com.rodrigo.login.contract.login.response.LoginResponse;
import com.rodrigo.login.implementation.services.login.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Builder
@Tag(name = "Auth", description = "Auth operations for user")
public class LoginController {

    private final AuthService postLoginService;

    @Operation(summary = "Login", description = "User login operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest payload){
        return postLoginService.postLogin(payload);
    }
}
