package com.reservation.reservation.domain;

import java.time.LocalDateTime;
import java.util.Objects;

record CancellationTime(LocalDateTime value) {
    public CancellationTime {
        Objects.requireNonNull(value);
    }
}
