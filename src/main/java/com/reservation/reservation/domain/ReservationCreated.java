package com.reservation.reservation.domain;

import java.time.Instant;

record ReservationCreated(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId,
                          ReservationTime reservedAt, Instant createdAt) implements ReservationEvent {

    public ReservationCreated(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId, ReservationTime reservedAt) {
        this(reservationId, clientId, sessionOccurrenceId, reservedAt, Instant.now());
    }
}
