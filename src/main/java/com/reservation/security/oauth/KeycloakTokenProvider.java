package com.reservation.security.oauth;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class KeycloakTokenProvider {

    private static final String REGISTRATION_ID = "kamann";

    private final RestClient restClient;
    private final OauthProperties oauthProperties;

    /**
     * Constructs a KeycloakTokenProvider with the specified REST client and OAuth properties.
     */
    public KeycloakTokenProvider(RestClient restClient, OauthProperties oauthProperties) {
        this.restClient = restClient;
        this.oauthProperties = oauthProperties;
    }

    /**
     * Obtains an OAuth2 access token from the Keycloak server using the client credentials grant type.
     *
     * @return the access token as a string
     * @throws IllegalStateException if the client registration for "kamann" is not found
     * @throws RuntimeException if the access token cannot be retrieved from the Keycloak response
     */
    public String getAccessToken() {
        var registration = oauthProperties.getClient().getRegistration().get(REGISTRATION_ID);
        if (registration == null) {
            throw new IllegalStateException("Client registration 'kamann' not found");
        }

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
