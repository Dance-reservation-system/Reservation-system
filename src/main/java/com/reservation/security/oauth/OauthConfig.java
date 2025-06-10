package com.reservation.security.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OauthConfig {

    @Bean
    public RestClient oauthTokenProvider(OauthProperties oauthProperties) {
        return RestClient.builder()
                .baseUrl(oauthProperties.getBaseUrl())
                .build();
    }

    @Bean
    public KeycloakTokenProvider keycloakTokenProvider(RestClient restClient, OauthProperties oauthProperties) {
        return new KeycloakTokenProvider(restClient, oauthProperties);
    }
}
