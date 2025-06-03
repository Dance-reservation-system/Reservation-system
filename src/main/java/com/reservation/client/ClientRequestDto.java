package com.reservation.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

record ClientRequestDto(
        @Size(min = 2)
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email
) {
}
