package com.rodrigo.login.contract.user.request;

import jakarta.validation.constraints.NotBlank;

public record PostUserRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
