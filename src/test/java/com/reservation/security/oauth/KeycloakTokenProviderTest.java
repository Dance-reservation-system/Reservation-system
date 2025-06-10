package com.reservation.security.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("keycloak-test")
class KeycloakTokenProviderTest extends KeycloakIT {

    @Autowired
    private KeycloakTokenProvider keycloakTokenProvider;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.client.registration.kamann.client-id", () -> TARGET_CLIENT_ID);
        registry.add("spring.security.oauth2.client.registration.kamann.client-secret", () -> {
            try {
                String adminToken = getAdminAccessToken();
                String clientUuid = getClientUuidByClientId(adminToken, TARGET_REALM, TARGET_CLIENT_ID);
                return getClientSecret(adminToken, TARGET_REALM, clientUuid);
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch client secret dynamically", e);
            }
        });

        registry.add("spring.security.oauth2.base-url", () -> BASE_URL + "/realms/" + TARGET_REALM + "/protocol/openid-connect");
        registry.add("spring.security.oauth2.client.provider.kamann.issuer-uri", () ->
                keycloak.getAuthServerUrl() + "/realms/" + TARGET_REALM);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () ->
                keycloak.getAuthServerUrl() + "/realms/" + TARGET_REALM);
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", () ->
                keycloak.getAuthServerUrl() + "/realms/" + TARGET_REALM + "/protocol/openid-connect/certs");
    }

    @Test
    void getAccessToken_shouldReturnToken() {
        String token = keycloakTokenProvider.getAccessToken();
        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("Access token: " + token);
    }
}
