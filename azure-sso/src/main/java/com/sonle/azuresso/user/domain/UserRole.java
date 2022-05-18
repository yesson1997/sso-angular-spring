package com.sonle.azuresso.user.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
