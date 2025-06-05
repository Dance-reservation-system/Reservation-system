package com.reservation.client;

import java.time.Instant;
import java.util.UUID;

class ClientTestFactory {

    public static final Instant FIXED_INSTANT = Instant.parse("2024-01-01T12:00:00Z");
    public static final UUID ID = UUID.fromString("07392a26-375d-4794-9eed-44a57c7efd41");

    static Client expectedTestClient() {
        var client = Client.builder()
                .name("client-1")
                .email("client-1@example.com")
                .build();

        client.setId(ID);
        client.setCreatedAt(FIXED_INSTANT);
        return client;
    }
}
