package com.reservation.reservation.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.reservation.domain.exception.ReservationAlreadyCancelledException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Reservation implements AggregateRoot<ReservationEvent> {
    private final ReservationId reservationId;
    private final ClientId clientId;
    private final SessionOccurrenceId sessionOccurrenceId;
    private ReservationStatus reservationStatus;
    private final ReservationTime reservedAt;
    private CancellationTime cancelledAt;

    private final ArrayList<ReservationEvent> reservationEvents = new ArrayList<>();

    private Reservation(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId, ReservationTime reservedAt) {
        this.reservationId = Objects.requireNonNull(reservationId);
        this.clientId = Objects.requireNonNull(clientId);
        this.sessionOccurrenceId = Objects.requireNonNull(sessionOccurrenceId);
        this.reservedAt = Objects.requireNonNull(reservedAt);
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    public static Reservation create(ReservationId reservationId, ClientId clientId, SessionOccurrenceId sessionOccurrenceId, ReservationTime reservedAt) {
        Reservation reservation = new Reservation(reservationId, clientId, sessionOccurrenceId, reservedAt);
        reservation.registerEvent(new ReservationCreated(reservationId, clientId, sessionOccurrenceId, reservedAt));
        return reservation;
    }

    public ReservationId getReservationId() {
        return this.reservationId;
    }

    public ClientId getClientId() {
        return this.clientId;
    }

    public void cancel(CancellationTime cancelledAt, CancellationPolicy cancellationPolicy) {
        if (!isActive()) {
            throw new ReservationAlreadyCancelledException();
        }
        this.cancelledAt = Objects.requireNonNull(cancelledAt);
        Objects.requireNonNull(cancellationPolicy);

        this.reservationStatus = cancellationPolicy.isLate(reservedAt, cancelledAt) ? ReservationStatus.CANCELLED_LATE : ReservationStatus.CANCELLED_EARLY;

        registerEvent(new ReservationCancelled(reservationId, clientId, sessionOccurrenceId, this.reservationStatus, cancelledAt, reservedAt));
    }

    public boolean isCancellableAt(ReservationTime now, CancellationPolicy policy) {
        return isActive() && policy.isCancellableAt(reservedAt, now);
    }

    public boolean wasCancelledBefore(LocalDateTime now, CancellationPolicy policy) {
        return policy.wasCancelledBefore(Optional.ofNullable(cancelledAt), now);
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
