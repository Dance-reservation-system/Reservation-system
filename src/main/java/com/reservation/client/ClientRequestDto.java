package com.reservation.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

record ClientRequestDto(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email
) {
}
