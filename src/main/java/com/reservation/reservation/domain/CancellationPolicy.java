package com.reservation.reservation.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class CancellationPolicy {

    private final Duration earlyCancellationThreshold;

    public CancellationPolicy(Duration earlyCancellationThreshold) {
        this.earlyCancellationThreshold = Objects.requireNonNull(earlyCancellationThreshold);
    }

    public boolean isLate(ReservationTime reservedAt, CancellationTime cancelledAt) {
        return cancelledAt.value().isAfter(reservedAt.value().minus(earlyCancellationThreshold));
    }

    public boolean isCancellableAt(ReservationTime reservedAt, ReservationTime now) {
        return now.value().isBefore(reservedAt.value().minus(earlyCancellationThreshold));
    }

    public boolean wasCancelledBefore(Optional<CancellationTime> cancelledAt, LocalDateTime pointInTime) {
        return cancelledAt.map(c -> c.value().isBefore(pointInTime)).orElse(false);
    }
}
