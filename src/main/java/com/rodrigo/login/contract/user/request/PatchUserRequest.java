package com.rodrigo.login.contract.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.Optional;

public record PatchUserRequest(
        Optional<String> name,
        Optional<@Pattern(regexp = "^[a-zA-Z0-9_-]{3,15}$", message = "Username must be alphanumeric and between 3 and 15 characters")String> username,
        Optional<@Email(message = "Email is invalid")String> email
) {}
