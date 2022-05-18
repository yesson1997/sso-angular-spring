package com.sonle.azuresso.controller.reqres;

import com.sonle.azuresso.user.domain.UserRole;

import java.util.List;

public class ResponseUser {
    private String username;
    private String email;
    private List<UserRole> userRoles;

    public ResponseUser() {
    }

    public ResponseUser(String username, String email, List<UserRole> userRoles) {
        this.username = username;
        this.email = email;
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
