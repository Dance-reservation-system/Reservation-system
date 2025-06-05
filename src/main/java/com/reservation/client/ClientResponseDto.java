package com.reservation.client;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ClientResponseDto(
        UUID id,
        String name,
        String email,
        Instant createdAt
) {

    static ClientResponseDto mapToDto(Client client) {
        return ClientResponseDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .createdAt(client.getCreatedAt())
                .build();
    }
}