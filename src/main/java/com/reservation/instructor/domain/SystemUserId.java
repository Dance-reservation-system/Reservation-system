package com.reservation.instructor.domain;

import java.util.Objects;
import java.util.UUID;

record SystemUserId(UUID value) {
    public SystemUserId {
        Objects.requireNonNull(value);
    }

    public static SystemUserId next() {
        return new SystemUserId(UUID.randomUUID());
    }
}
