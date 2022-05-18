package com.sonle.azuresso.controller.reqres;

public class SuccessAuthenticationResponse {
    private PublicUser user;
    private String token;

    private SuccessAuthenticationResponse() {
    }

    public SuccessAuthenticationResponse(PublicUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
