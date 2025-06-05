package com.reservation.client;

import java.time.Instant;

class ClientTestFactory {

    public static final Instant FIXED_INSTANT = Instant.parse("2024-01-01T12:00:00Z");

    static Client expectedTestClient() {
        var client = Client.builder()
                .name("client-1")
                .email("client-1@example.com")
                .build();

        client.setId(1L);
        client.setCreatedAt(FIXED_INSTANT);
        return client;
    }
}
