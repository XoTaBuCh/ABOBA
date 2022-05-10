package com.javamaster.spring_crud.model.user;

public enum Permission {
    PERMISSION_LEVEL_1("permission1"),
    PERMISSION_LEVEL_2("permission2"),
    PERMISSION_LEVEL_3("permission3");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
