package com.reservation.reservation.domain;

import com.reservation.reservation.domain.exception.ReservationAlreadyCancelledException;
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
    private final CancellationPolicy policy = new CancellationPolicy(Duration.ofHours(2));
    private final ReservationTime reservedAt = new ReservationTime(LocalDateTime.now().plusDays(1));
    private Reservation reservation;
    private List<ReservationEvent> events;

    @Test
    void shouldCreateValidReservation() {
        reservation = new ReservationTestBuilder().build();

        events = reservation.pullEvents();

        ReservationCreated reservationCreated = events.stream()
                .filter(ReservationCreated.class::isInstance)
                .map(ReservationCreated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ReservationCreated event"));

        assertAll(
                () -> assertTrue(reservation.isActive()),
                () -> assertNotNull(reservationCreated.createdAt())
        );
    }

    @Test
    void shouldCancelEarly() {
        reservation = new ReservationTestBuilder().build();
        CancellationTime cancellationTime = new CancellationTime(reservedAt.minusHours(4).value());

        reservation.cancel(cancellationTime, policy);
        events = reservation.pullEvents();

        ReservationCancelled reservationCancelled = events.stream()
                .filter(ReservationCancelled.class::isInstance)
                .map(ReservationCancelled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ReservationCancelled event"));

        assertAll(
                () -> assertEquals(ReservationStatus.CANCELLED_EARLY, reservationCancelled.status()),
                () -> assertFalse(reservation.isActive())
        );
    }

    @Test
    void shouldCancelLate() {
        reservation = new ReservationTestBuilder().build();
        CancellationTime cancellationTime = new CancellationTime(reservedAt.minusMinutes(15).value());

        reservation.cancel(cancellationTime, policy);
        events = reservation.pullEvents();

        ReservationCancelled reservationCancelled = events.stream()
                .filter(ReservationCancelled.class::isInstance)
                .map(ReservationCancelled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ReservationCancelled event"));

        assertAll(
                () -> assertEquals(ReservationStatus.CANCELLED_LATE, reservationCancelled.status()),
                () -> assertFalse(reservation.isActive())
        );
    }

    @Test
    void shouldThrowWhenTryingToCancelAlreadyCancelledReservation() {
        reservation = new ReservationTestBuilder().build();
        CancellationTime cancellationTime = new CancellationTime(reservedAt.minusHours(3).value());

        reservation.cancel(cancellationTime, policy);

        assertThrows(ReservationAlreadyCancelledException.class, () ->
                reservation.cancel(new CancellationTime(reservedAt.minusHours(1).value()), policy)
        );
    }

    @Test
    void shouldReturnFalseWhenIsNotCancellableWithinWindow() {
        reservation = new ReservationTestBuilder().build();
        ReservationTime now = reservedAt.minusMinutes(30);

        boolean result = reservation.isCancellableAt(now, policy);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenIsCancellableWithinWindow() {
        reservation = new ReservationTestBuilder().build();
        ReservationTime now = reservedAt.minusHours(3);

        boolean result = reservation.isCancellableAt(now, policy);

        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenWasCancelledBeforeGivenTime() {
        reservation = new ReservationTestBuilder().build();
        CancellationTime cancellationTime = new CancellationTime(reservedAt.minusHours(3).value());

        reservation.cancel(cancellationTime, policy);

        boolean result = reservation.wasCancelledBefore(LocalDateTime.now().plusDays(2), policy);

        assertTrue(result);
    }
}
