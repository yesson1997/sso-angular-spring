package com.sonle.azuresso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sso.azure")
public class SSOConfig {
    private String clientId;
    private String secretId;
    private String authUrl;
    private String tokenUrl;
    private String resourceUrl;
    private String backendRedirectUrl;
    private String frontendRedirectUrl;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getBackendRedirectUrl() {
        return backendRedirectUrl;
    }

    public void setBackendRedirectUrl(String backendRedirectUrl) {
        this.backendRedirectUrl = backendRedirectUrl;
    }

    public String getFrontendRedirectUrl() {
        return frontendRedirectUrl;
    }

    public void setFrontendRedirectUrl(String frontendRedirectUrl) {
        this.frontendRedirectUrl = frontendRedirectUrl;
    }
}
