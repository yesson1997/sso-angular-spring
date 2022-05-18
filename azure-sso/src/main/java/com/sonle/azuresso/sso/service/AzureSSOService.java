package com.sonle.azuresso.sso.service;

import com.sonle.azuresso.config.SSOConfig;
import com.sonle.azuresso.sso.domain.SSOUser;
import com.sonle.azuresso.user.domain.UserAndToken;
import com.sonle.azuresso.user.service.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class AzureSSOService {
    private final RestTemplate restTemplate;
    private final SSOConfig ssoConfig;
    private final UserService userService;

    public AzureSSOService(RestTemplate restTemplate, SSOConfig ssoConfig, UserService userService) {
        this.restTemplate = restTemplate;
        this.ssoConfig = ssoConfig;
        this.userService = userService;
    }

    public String buildAuthenticationUrl() {
        String authUrl = "%s" +
                "?client_id=%s" +
                "&response_type=code" +
                "&redirect_uri=%s" +
                "&response_mode=form_post" +
                "&scope=openid email profile" +
                "&state=%s";
        return String.format(authUrl, ssoConfig.getAuthUrl(), ssoConfig.getClientId(), ssoConfig.getBackendRedirectUrl(), getRandomState());
    }

    public String handleSigninForSsoUser(String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        SSOUser ssoUser = retrieveUserInfo(accessToken);
        UserAndToken userAndToken = userService.processSigninForSso(ssoUser.getUserPrincipalName());
        return String.format("%s?token=%s", ssoConfig.getFrontendRedirectUrl(), userAndToken.getToken());
    }

    private String getAccessToken(String authorizationCode) {
        ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<Map<String, String>>() {};
        HttpEntity<MultiValueMap<String, String>> requestEntity = buildRequestEntity(ssoConfig.getBackendRedirectUrl(), authorizationCode);
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(ssoConfig.getTokenUrl(), HttpMethod.POST, requestEntity, responseType);
        Map<String, String> body = response.getBody();
        if (body == null || !body.containsKey("access_token") || body.get("access_token") == null) {
            throw new RuntimeException("Empty access token");
        }
        return body.get("access_token");
    }

    private SSOUser retrieveUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<SSOUser> response = new RestTemplate()
                .exchange(ssoConfig.getResourceUrl(), HttpMethod.GET, request, SSOUser.class);
        SSOUser ssoUser = response.getBody();
        return ssoUser;
    }

    private HttpEntity<MultiValueMap<String, String>> buildRequestEntity(String redirectUrl, String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", ssoConfig.getClientId());
        map.add("response_type", "code");
        map.add("redirect_uri", redirectUrl);
        map.add("code", authorizationCode);
        map.add("grant_type", "authorization_code");
        map.add("response_mode", "form_post");
        map.add("scope", "openid email profile");
        map.add("client_secret", ssoConfig.getSecretId());
        return new HttpEntity<>(map, headers);
    }

    private String getRandomState() {
        return UUID.randomUUID().toString();
    }
}
