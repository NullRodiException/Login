package com.rodrigo.login.contract.auth;

import com.rodrigo.login.contract.exception.response.ExceptionResponse;
import com.rodrigo.login.contract.auth.request.ChangePasswordRequest;
import com.rodrigo.login.contract.auth.request.LoginRequest;
import com.rodrigo.login.implementation.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth operations for user")
public class AuthController {

    private final AuthService postLoginService;

    public AuthController(AuthService postLoginService) {
        this.postLoginService = postLoginService;
    }

    @Operation(summary = "Login", description = "User login operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest payload,
            HttpServletResponse httpServletResponse){
        String token = postLoginService.postLogin(payload);
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Change password", description = "Change user password operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable String id,
            @Valid @RequestBody ChangePasswordRequest payload){
        postLoginService.changePassword(id, payload);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
