package com.reservation.event.domain;

import java.util.Objects;
import java.util.UUID;

record SessionOccurrenceId(UUID value) {
    public SessionOccurrenceId {
        Objects.requireNonNull(value);
    }

    public static UUID next() {
        return UUID.randomUUID();
    }
}
