package com.reservation.reservation.domain;

import java.util.Objects;
import java.util.UUID;

record ReservationId(UUID value) {
    public ReservationId {
        Objects.requireNonNull(value);
    }

    public static ReservationId next() {
        return new ReservationId(UUID.randomUUID());
    }
}
