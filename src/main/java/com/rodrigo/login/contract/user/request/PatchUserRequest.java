package com.rodrigo.login.contract.user.request;

import java.util.Optional;

public record PatchUserRequest(
        Optional<String> name,
        Optional<String> username,
        Optional<String> email,
        Optional<String> hashedPassword
) {}
