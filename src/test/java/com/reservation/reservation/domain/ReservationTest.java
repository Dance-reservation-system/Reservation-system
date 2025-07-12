package com.reservation.reservation.domain;

import com.reservation.reservation.domain.exception.ReservationAlreadyCancelledException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReservationTest {
    private Reservation reservation;
    private ReservationId reservationId;
    private ClientId clientId;
    private SessionOccurrenceId sessionOccurrenceId;
    private LocalDateTime reservedAt;
    private List<ReservationEvent> events;

    @BeforeEach
    void setUp() {
        reservationId = ReservationId.next();
        clientId = ClientId.next();
        sessionOccurrenceId = SessionOccurrenceId.next();
        reservedAt = LocalDateTime.now().plusDays(1);
        reservation = Reservation.create(reservationId, clientId, sessionOccurrenceId, reservedAt);
    }

    @Test
    void shouldCreateValidReservation() {
        events = reservation.pullEvents();

        ReservationCreated reservationCreated = events.stream()
                .filter(ReservationCreated.class::isInstance)
                .map(ReservationCreated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ReservationCreated event"));

        assertAll(
                () -> assertEquals(reservationId, reservation.getReservationId()),
                () -> assertEquals(clientId, reservation.getClientId()),
                () -> assertEquals(sessionOccurrenceId, reservation.getSessionOccurrenceId()),
                () -> assertTrue(reservation.isActive()),
                () -> assertEquals(reservationId, reservationCreated.reservationId()),
                () -> assertNotNull(reservationCreated.createdAt())
        );
    }

    @Test
    void shouldCancelEarly() {
        LocalDateTime cancelTime = reservedAt.minusHours(4);
        boolean isLate = false;

        reservation.cancel(cancelTime, isLate);
        events = reservation.pullEvents();

        ReservationCancelled reservationCancelled = events.stream()
                .filter(ReservationCancelled.class::isInstance)
                .map(ReservationCancelled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ReservationCancelled event"));

        assertAll(
                () -> assertEquals(ReservationStatus.CANCELLED_EARLY, reservationCancelled.status()),
                () -> assertEquals(reservationId, reservationCancelled.reservationId()),
                () -> assertFalse(reservation.isActive())
        );
    }

    @Test
    void shouldCancelLate() {
        LocalDateTime cancelTime = reservedAt.minusMinutes(15);
        boolean isLate = true;

        reservation.cancel(cancelTime, isLate);
        events = reservation.pullEvents();

        ReservationCancelled reservationCancelled = events.stream()
                .filter(ReservationCancelled.class::isInstance)
                .map(ReservationCancelled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ReservationCancelled event"));

        assertAll(
                () -> assertEquals(ReservationStatus.CANCELLED_LATE, reservationCancelled.status()),
                () -> assertEquals(reservationId, reservationCancelled.reservationId()),
                () -> assertFalse(reservation.isActive())
        );
    }

    @Test
    void shouldThrowWhenTryingToCancelAlreadyCancelledReservation() {
        reservation.cancel(reservedAt.minusHours(2), false);

        assertThrows(ReservationAlreadyCancelledException.class, () ->
                reservation.cancel(reservedAt.minusHours(1), true)
        );
    }

    @Test
    void shouldReturnFalseWhenIsNotCancellableWithinWindow() {
        Duration window = Duration.ofHours(2);
        LocalDateTime now = reservedAt.minusMinutes(30);

        boolean result = reservation.isCancellableAt(now, window);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenIsCancellableWithinWindow() {
        Duration window = Duration.ofHours(2);
        LocalDateTime now = reservedAt.minusHours(3);

        boolean result = reservation.isCancellableAt(now, window);

        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenWasCancelledBeforeGivenTime() {
        LocalDateTime cancelTime = reservedAt.minusHours(3);
        reservation.cancel(cancelTime, false);

        boolean result = reservation.wasCancelledBefore(LocalDateTime.now().plusDays(2));

        assertTrue(result);
    }
}
