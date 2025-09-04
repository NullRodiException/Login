package com.rodrigo.login.contract.auth.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "{user.login.blank}")
        String login,
        @NotBlank(message = "{user.password.blank}")
        String password
) {
}
