package com.reservation.security;

import com.reservation.security.oauth.KeycloakTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class TestController {

    private final KeycloakTokenProvider tokenProvider;

    public TestController(KeycloakTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/check")
    public String check() {
        return "OK";
    }

    @GetMapping("/token")
    public String token() {
        return tokenProvider.getAccessToken();
    }
}
