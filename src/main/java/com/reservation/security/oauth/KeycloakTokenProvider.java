package com.reservation.security.oauth;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class KeycloakTokenProvider {

    private final RestClient restClient;
    private final OauthProperties oauthProperties;

    public KeycloakTokenProvider(RestClient restClient, OauthProperties oauthProperties) {
        this.restClient = restClient;
        this.oauthProperties = oauthProperties;
    }

    public String getAccessToken() {
        var registration = oauthProperties.getClient().getRegistration().get(oauthProperties.getRegistrationName());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", registration.clientId());
        formData.add("client_secret", registration.clientSecret());

        Map<String, Object> response = restClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(Map.class);

        if (response != null && response.containsKey("access_token")) {
            return (String) response.get("access_token");
        } else {
            throw new RuntimeException("Failed to retrieve access token from Keycloak");
        }
    }
}
