package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.InvalidCancellationThresholdException;

import java.time.Duration;
import java.util.Objects;

record CancellationPolicy(Duration duration) {
    CancellationPolicy {
        Objects.requireNonNull(duration);
        if (!duration.isPositive()) {
            throw new InvalidCancellationThresholdException(duration);
        }
    }
}
