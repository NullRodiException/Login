package com.rodrigo.login.contract.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PostUserRequest(
        @NotBlank(message = "{user.name.blank}")
        String name, // Changed message to a more descriptive one
        @NotBlank(message = "Username cannot be blank")
        @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$",
                message = "Username must be 3-15 characters long and contain only letters, numbers, or underscores")
        String username,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long")
        @Pattern(
                regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{P}\\p{S}]).+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
        String password
) {
}
