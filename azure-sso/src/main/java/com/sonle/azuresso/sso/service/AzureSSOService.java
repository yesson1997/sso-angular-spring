package com.sonle.azuresso.sso;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AzureSSOService {
    private final RestTemplate restTemplate;

    public AzureSSOService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String clientId = "9e1dd129-98c7-4476-8cf8-6430c0374fbd";
    private String secretId = "OL-8Q~puVlhJqTkRMkIv_iJzYyA~szMGhYGPQcG2";

    public String buildAuthenticationUrl(String redirectUrl) {
        String loginUrl = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize" +
                "?client_id=%s" +
                "&response_type=code" +
                "&redirect_uri=%s" +
                "&response_mode=form_post" +
                "&scope=openid email profile" +
                "&state=%s";
        return String.format(loginUrl, clientId, redirectUrl, getRandomState());
    }

    public String getAccessToken(String redirectUrl, String authorizationCode) {
//        String url = String.format("https://login.microsoftonline.com/consumers/oauth2/v2.0/token" +
//                "?client_id=%s" +
//                "&response_type=code" +
//                "&redirect_uri=%s" +
//                "&code=%s" +
//                "&grant_type=authorization_code" +
//                "&response_mode=form_post" +
//                "&scope=openid email profile" +
//                "&client_secret=%s", clientId, redirectUrl, authorizationCode, secretId);

        String url = "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
        ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<Map<String, String>>() {};
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, buildRequestBody(redirectUrl, authorizationCode), responseType);

        for (Map.Entry<String, String> entry : response.getBody().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        Map<String, String> body = response.getBody();
        if (!body.containsKey("access_token") || body.get("access_token") == null) {
            throw new RuntimeException("Empty access token");
        }
        String token = response.getBody().get("access_token");


        return getRandomState();
    }

    private HttpEntity<MultiValueMap<String, String>> buildRequestBody(String redirectUrl, String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", clientId);
        map.add("response_type", "code");
        map.add("redirect_uri", redirectUrl);
        map.add("code", authorizationCode);
        map.add("grant_type", "authorization_code");
        map.add("response_mode", "form_post");
        map.add("scope", "openid email profile");
        map.add("client_secret", secretId);
        return new HttpEntity<MultiValueMap<String, String>>(map, headers);
    }
    
    private String getRandomState() {
//        return UUID.randomUUID().toString();
        return "416d2cb6-f98b-4660-a6be-5766c6d70c9b";
    }
}
