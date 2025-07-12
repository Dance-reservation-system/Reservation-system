package com.reservation.reservation.domain;

import java.util.Objects;
import java.util.UUID;

record SessionOccurrenceId(UUID value) {
    public SessionOccurrenceId {
        Objects.requireNonNull(value);
    }

    public static SessionOccurrenceId next() {
        return new SessionOccurrenceId(UUID.randomUUID());
    }
}
