package com.reservation.owner;

import jakarta.validation.constraints.NotBlank;

record OwnerRequestDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}