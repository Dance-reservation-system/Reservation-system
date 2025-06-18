package com.reservation.security;

import com.reservation.security.oauth.KeycloakTokenProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"keycloak-test", "local"})
@RestController
@RequestMapping()
public class TestController {

    private final KeycloakTokenProvider tokenProvider;

    public TestController(KeycloakTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/test")
    public String token() {
        return tokenProvider.getAccessToken();
    }
}
