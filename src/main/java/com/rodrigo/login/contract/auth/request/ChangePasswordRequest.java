package com.rodrigo.login.contract.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "{user.password.old}")
        String password,
        @Size(min = 8, max = 64, message = "{user.password.size}")
        @Pattern(
                regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{P}\\p{S}]).+$",
                message = "{user.password.pattern}")
        String newPassword
) {
}
