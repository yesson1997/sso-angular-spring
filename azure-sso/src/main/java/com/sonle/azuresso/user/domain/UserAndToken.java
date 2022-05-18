package com.sonle.azuresso.user.domain;

import com.sonle.azuresso.user.entity.User;

public class UserAndToken {
    private User user;
    private String token;

    private UserAndToken() {}

    public UserAndToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
