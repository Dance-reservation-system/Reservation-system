package com.reservation.reservation.domain;

import java.time.Instant;

record ReservationCancelled(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId,
                            ReservationStatus status, CancellationTime cancelledAt, ReservationTime reservedAt,
                            Instant eventCreatedAt) implements ReservationEvent {

    public ReservationCancelled(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId, ReservationStatus status, CancellationTime cancelledAt, ReservationTime reservedAt) {
        this(reservationId, clientId, sessionOccurrenceId, status, cancelledAt, reservedAt, Instant.now());
    }
}
