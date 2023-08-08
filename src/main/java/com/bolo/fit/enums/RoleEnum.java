package com.bolo.fit.enums;

public enum RoleEnum {
    COMUM_USER("COMUM_USER"),
    PERSONAL_TRAINER("PERSONAL_TRAINER"),
    ADMIN("ADMIN");

    private String description;

    RoleEnum(String description) {
        this.description = description;
    }
}
