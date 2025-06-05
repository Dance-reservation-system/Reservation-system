package com.reservation.client;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
record UpdateClientRequestDto(
        String name,
        @Email
        String email
) {
}
