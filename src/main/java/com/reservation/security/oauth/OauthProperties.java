package com.reservation.security.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2")
public class OauthProperties {

    private String baseUrl;
    private Resourceserver resourceserver;
    private Client client;

    public record Resourceserver(Jwt jwt) {
        public record Jwt(String issuerUri, String jwkSetUri) {}
    }

    @Getter
    @Setter
    public static class Client {
        private Map<String, ClientRegistration> registration;
        private Map<String, ClientProvider> provider;
    }

    public record ClientRegistration(String clientId, String clientSecret, String redirectUri,
                                     String authorizationGrantType, List<String> scope) {}

    public record ClientProvider(String authorizationUri, String tokenUri, String jwkSetUri,
                                 String userInfoUri, String userNameAttribute) {}
}
