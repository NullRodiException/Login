package com.rodrigo.login.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashPassword {
    private final PasswordEncoder encoder;

    public HashPassword(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String hashPassword(String password){
        return encoder.encode(password);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
