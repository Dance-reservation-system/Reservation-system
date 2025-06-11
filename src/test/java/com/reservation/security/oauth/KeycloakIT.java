package com.reservation.security.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.AbstractIT;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class KeycloakIT extends AbstractIT {

    public static String BASE_URL;
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password";

    public static final String MASTER_REALM = "master";
    public static final String TARGET_REALM = "kamann-test";
    public static final String TARGET_CLIENT_ID = "kamann-test-keycloak-id";

    public static KeycloakContainer keycloak;
    private static HttpClient httpClient;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void startKeycloak() throws Exception {
        keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:26.2")
                .withRealmImportFile("keycloak/test-realm-export.json")
                .withAdminUsername(ADMIN_USERNAME)
                .withAdminPassword(ADMIN_PASSWORD);

        keycloak.start();
        BASE_URL = keycloak.getAuthServerUrl();

        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();

        String adminToken = getAdminAccessToken();

        String clientUuid = getClientUuidByClientId(adminToken, TARGET_REALM, TARGET_CLIENT_ID);

        String generatedSecret = generateNewClientSecret(adminToken, TARGET_REALM, clientUuid);
        System.out.println("Generated client secret: " + generatedSecret);

        String secret = getClientSecret(adminToken, TARGET_REALM, clientUuid);
        System.out.println("Client secret from GET: " + secret);
        assertNotNull(secret);
        assertFalse(secret.isEmpty());
    }

    @AfterAll
    static void stopKeycloak() {
        if (keycloak != null) {
            keycloak.stop();
        }
    }

    @Test
    void keycloakIsRunning() {
        assertTrue(keycloak.isRunning());
        assertNotNull(keycloak.getAuthServerUrl());
    }

    public static String getAdminAccessToken() throws IOException, InterruptedException {
        String authUrl = keycloak.getAuthServerUrl() + "/realms/" + MASTER_REALM + "/protocol/openid-connect/token";

        String form = "grant_type=password"
                + "&client_id=admin-cli"
                + "&username=" + URLEncoder.encode(ADMIN_USERNAME, StandardCharsets.UTF_8)
                + "&password=" + URLEncoder.encode(ADMIN_PASSWORD, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get admin token: " + response.body());
        }
        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode.get("access_token").asText();
    }

    public static String getClientUuidByClientId(String adminToken, String realm, String clientId) throws IOException, InterruptedException {
        String url = keycloak.getAuthServerUrl() + "/admin/realms/" + realm + "/clients?clientId=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + adminToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get client by clientId: " + response.body());
        }

        JsonNode clients = objectMapper.readTree(response.body());
        if (clients.isEmpty()) {
            throw new RuntimeException("Client not found with clientId: " + clientId);
        }

        return clients.get(0).get("id").asText();
    }

    private static String generateNewClientSecret(String adminToken, String realm, String clientUuid) throws IOException, InterruptedException {
        String url = keycloak.getAuthServerUrl() + "/admin/realms/" + realm + "/clients/" + clientUuid + "/client-secret";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + adminToken)
                .POST(HttpRequest.BodyPublishers.noBody())  // POST bez body generuje nowy secret
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to generate client secret: " + response.body());
        }

        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode.get("value").asText();
    }

    public static String getClientSecret(String adminToken, String realm, String clientUuid) throws IOException, InterruptedException {
        String url = keycloak.getAuthServerUrl() + "/admin/realms/" + realm + "/clients/" + clientUuid + "/client-secret";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + adminToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get client secret: " + response.body());
        }

        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode.get("value").asText();
    }
}
