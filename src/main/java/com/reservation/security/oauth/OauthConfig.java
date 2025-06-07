package com.reservation.security.oauth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

    /**
     * Creates and configures a RestClient for OAuth token operations using the base URL from OauthProperties.
     *
     * @return a RestClient instance configured with the OAuth server's base URL
     */
    @Bean
    public RestClient oauthTokenProvider(OauthProperties oauthProperties) {
        return RestClient.builder()
                .baseUrl(oauthProperties.getBaseUrl())
                .build();
    }

    /**
     * Creates a KeycloakTokenProvider bean initialized with the provided RestClient and OauthProperties.
     *
     * @return a configured KeycloakTokenProvider instance
     */
    @Bean
    public KeycloakTokenProvider keycloakTokenProvider(RestClient restClient, OauthProperties oauthProperties) {
        return new KeycloakTokenProvider(restClient, oauthProperties);
    }
}
