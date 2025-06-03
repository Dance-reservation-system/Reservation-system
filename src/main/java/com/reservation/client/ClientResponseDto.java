package com.reservation.client;

import java.time.Instant;

public record ClientResponseDto(
        Long id,
        String name,
        String email,
        Instant createdAt
) {

    static ClientResponseDto mapToDto(Client client) {
        return new ClientResponseDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCreatedAt()
        );
    }
}