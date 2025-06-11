package com.reservation;

import com.reservation.security.oauth.OauthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class KamannModularApplication {
    public static void main(String[] args) {
        SpringApplication.run(KamannModularApplication.class, args);
    }
}
