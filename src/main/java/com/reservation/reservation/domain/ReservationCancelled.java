package com.reservation.reservation.domain;

import java.time.Instant;

record ReservationCancelled(ReservationId reservationId, ReservationStatus status, Instant cancelledAt) implements ReservationEvent {
    public ReservationCancelled (ReservationId reservationId, ReservationStatus status) {
        this(reservationId, status, Instant.now());
    }
}
