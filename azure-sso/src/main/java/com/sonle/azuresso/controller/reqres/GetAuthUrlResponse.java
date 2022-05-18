package com.sonle.azuresso.controller.reqres;

public class GetAuthUrlResponse {
    private String authUrl;

    private GetAuthUrlResponse() {
    }

    public GetAuthUrlResponse(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
