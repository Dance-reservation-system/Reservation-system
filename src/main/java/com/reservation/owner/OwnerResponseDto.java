package com.reservation.owner;

import java.time.Instant;

public record OwnerResponseDto(
        Long id,
        String firstName,
        String lastName,
        Instant createdAt
) {
    static OwnerResponseDto mapToDto(Owner owner) {
        return new OwnerResponseDto(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getCreatedAt()
        );
    }
}