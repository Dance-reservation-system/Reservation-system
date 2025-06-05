package com.reservation.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

record CreateClientRequestDto(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email
) {
}
