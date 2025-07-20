package com.reservation.payment.domain;

import java.util.Objects;

record FailureReason(String value) {
    public FailureReason {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException("Failure reason cannot be blank");
        }
    }
}