package com.rodrigo.login.common.enums;

public enum UserRoleEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    public String getName() {
        return role;
    }
}
