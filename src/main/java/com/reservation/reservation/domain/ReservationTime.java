package com.reservation.reservation.domain;

import java.time.LocalDateTime;
import java.util.Objects;

record ReservationTime(LocalDateTime value) {
    public ReservationTime {
        Objects.requireNonNull(value);
    }

    public ReservationTime minusHours(long hours) {
        return new ReservationTime(value.minusHours(hours));
    }

    public ReservationTime minusMinutes(long minutes) {
        return new ReservationTime(value.minusMinutes(minutes));
    }

    public LocalDateTime value() {
        return value;
    }
}
