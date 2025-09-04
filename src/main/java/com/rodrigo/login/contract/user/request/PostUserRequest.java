package com.rodrigo.login.contract.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PostUserRequest(
        @NotBlank(message = "{user.name.blank}")
        String name,
        @NotBlank(message = "{user.username.blank}")
        @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$",
                message = "{user.username.invalid}")
        String username,
        @NotBlank(message = "{user.email.blank}")
        @Email(message = "{user.email.invalid}")
        String email,
        @Size(min = 8, max = 64, message = "{user.password.size}")
        @Pattern(
                regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{P}\\p{S}]).+$",
                message = "{user.password.pattern}")
        String password
) {
}
