package com.reservation.event.domain;

import java.util.Objects;
import java.util.UUID;

record SessionId(UUID value) {
    public SessionId {
        Objects.requireNonNull(value);
    }

    public static SessionId next() {
        return new SessionId(UUID.randomUUID());
    }
}
