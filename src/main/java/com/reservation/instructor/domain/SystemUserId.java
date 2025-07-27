package com.reservation.instructor.domain;

import java.util.Objects;
import java.util.UUID;

record SystemUserId(UUID value) {
    SystemUserId {
        Objects.requireNonNull(value);
    }

    static SystemUserId next() {
        return new SystemUserId(UUID.randomUUID());
    }
}
