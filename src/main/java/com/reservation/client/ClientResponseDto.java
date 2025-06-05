package com.reservation.client;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ClientResponseDto(
        Long id,
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