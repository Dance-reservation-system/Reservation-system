package com.reservation.reservation.domain;

import java.time.LocalDateTime;

class ReservationTestBuilder {
    private ReservationId reservationId = ReservationId.next();
    private ClientId clientId = ClientId.next();
    private SessionOccurrenceId sessionOccurrenceId = SessionOccurrenceId.next();
    private ReservationTime reservedAt = new ReservationTime(LocalDateTime.now().plusDays(1));

    public Reservation build() {
        return Reservation.create(reservationId, clientId, sessionOccurrenceId, reservedAt);
    }
}
