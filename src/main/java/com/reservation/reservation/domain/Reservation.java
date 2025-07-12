package com.reservation.reservation.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.reservation.domain.exception.ReservationAlreadyCancelledException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservation implements AggregateRoot<ReservationEvent> {
    private final ReservationId reservationId;
    private final ClientId clientId;
    private final SessionOccurrenceId sessionOccurrenceId;
    private ReservationStatus reservationStatus;
    private LocalDateTime reservedAt;
    private LocalDateTime cancelledAt;

    private final ArrayList<ReservationEvent> reservationEvents = new ArrayList<>();

    private Reservation(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId, LocalDateTime reservedAt) {
        this.reservationId = Objects.requireNonNull(reservationId);
        this.clientId = Objects.requireNonNull(clientId);
        this.sessionOccurrenceId = Objects.requireNonNull(sessionOccurrenceId);
        this.reservedAt = Objects.requireNonNull(reservedAt);
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    public static Reservation create(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId, LocalDateTime reservedAt) {
        Reservation reservation = new Reservation(reservationId, clientId, sessionOccurrenceId, reservedAt);
        reservation.registerEvent(new ReservationCreated(reservationId));
        return reservation;
    }

    public ReservationId getReservationId() {
        return this.reservationId;
    }

    public ClientId getClientId() {
        return this.clientId;
    }

    public SessionOccurrenceId getSessionOccurrenceId() {
        return this.sessionOccurrenceId;
    }

    public void cancel(LocalDateTime cancelledAt, boolean isCancelLate) {
        if (!isActive()) {
            throw new ReservationAlreadyCancelledException("Reservation has been cancelled");
        }
        this.cancelledAt = Objects.requireNonNull(cancelledAt);

        this.reservationStatus = isCancelLate
                ? ReservationStatus.CANCELLED_LATE
                : ReservationStatus.CANCELLED_EARLY;

        registerEvent(new ReservationCancelled(reservationId, this.reservationStatus));
    }

    public boolean isCancellableAt(LocalDateTime now, Duration cancellationWindow) {
        Objects.requireNonNull(now);
        Objects.requireNonNull(cancellationWindow);
        return isActive() && now.isBefore(reservedAt.minus(cancellationWindow));
    }

    public boolean wasCancelledBefore(LocalDateTime now) {
        return cancelledAt != null && cancelledAt.isBefore(now);
    }

    @Override
    public List<ReservationEvent> pullEvents() {
        List<ReservationEvent> copyEvents = List.copyOf(reservationEvents);
        reservationEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(ReservationEvent event) {
        reservationEvents.add(Objects.requireNonNull(event));
    }

    public boolean isActive() {
        return this.reservationStatus.isActive();
    }
}
