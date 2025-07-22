package com.reservation.payment.domain;

import java.util.Objects;
import java.util.UUID;

record ReservationId(UUID value) {
    public ReservationId {
        Objects.requireNonNull(value);
    }

    static ReservationId next() {
        return new ReservationId(UUID.randomUUID());
    }
}