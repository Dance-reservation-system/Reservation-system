package com.reservation.reservation.domain;

import java.time.Instant;

record ReservationCreated(ReservationId reservationId, Instant createdAt) implements ReservationEvent {
    public ReservationCreated (ReservationId reservationId){
        this(reservationId, Instant.now());
    }
}
