package com.rodrigo.login.contract.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.Optional;

public record PatchUserRequest(
        Optional<String> name,
        Optional<@Pattern(
                regexp = "^[a-zA-Z0-9_-]{3,15}$",
                message = "Username must be 3-15 characters long and contain only letters, numbers, underscores, or hyphens.")
                String> username,
        Optional<@Email(
                message = "Invalid email format.")
                String> email
) {}
