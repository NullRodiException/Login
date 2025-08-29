package com.rodrigo.login.common.security;

import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class HashPassword {
    private final PasswordEncoder encoder;

    public String hashPassword(String password){
        return encoder.encode(password);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
