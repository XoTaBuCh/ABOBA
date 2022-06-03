package com.tabletka.model.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    CLIENT(Set.of(Permission.PERMISSION_LEVEL_1)),
    APOTHECARY(Set.of(Permission.PERMISSION_LEVEL_2)),
    ADMIN(Set.of(Permission.PERMISSION_LEVEL_3));

    private final Set<Permission> permission;

    Role(Set<Permission> permission) {
        this.permission = permission;
    }

    public Set<Permission> getPermission() {
        return permission;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermission().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
