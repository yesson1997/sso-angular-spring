package com.sonle.azuresso.controller.reqres;

import com.sonle.azuresso.user.domain.UserRole;

import java.util.List;

public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private List<UserRole> userRoles;

    public SignupRequest() {
    }

    public SignupRequest(String username, String password, String email, List<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
