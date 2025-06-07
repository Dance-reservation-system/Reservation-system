package com.reservation.security;

import com.reservation.security.oauth.KeycloakTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TestController {

    private final KeycloakTokenProvider tokenProvider;

    /**
     * Creates a new instance of TestController with the specified KeycloakTokenProvider.
     *
     * @param tokenProvider the provider used to obtain access tokens
     */
    public TestController(KeycloakTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Handles GET requests to "/test" and returns the current access token.
     *
     * @return the access token as a string
     */
    @GetMapping("/test")
    public String token() {
        return tokenProvider.getAccessToken();
    }
}
