package com.rodrigo.login.contract.user.request;

public record PostUserRequest(
        String name,
        String username,
        String email,
        String password
) {
}
