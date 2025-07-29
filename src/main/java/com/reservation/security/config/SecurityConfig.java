package com.reservation.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Profile({"keycloak-test", "local", "dev"})
    @Bean
    public SecurityFilterChain keycloakTestSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    @Configuration
    @Profile("test")
    public class NoSecurityConfig {

        @Bean
        public SecurityFilterChain noSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .securityMatcher("/**")
                    .httpBasic(basic -> basic.disable())
                    .formLogin(form -> form.disable())
                    .logout(logout -> logout.disable())
                    .oauth2ResourceServer(oauth2 -> oauth2.disable());

            return http.build();
        }
    }
}
