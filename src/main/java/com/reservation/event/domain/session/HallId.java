package com.reservation.event.domain.session;

import java.util.Objects;
import java.util.UUID;

record HallId(UUID value) {
    public HallId {
        Objects.requireNonNull(value);
    }

    public static HallId next() {
        return new HallId(UUID.randomUUID());
    }
}
