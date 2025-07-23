package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidFailureReasonException;

import java.util.Objects;

record FailureReason(String value) {
    public FailureReason {
        Objects.requireNonNull(value, "Failure reason cannot be null");
        if (value.isBlank()) {
            throw new InvalidFailureReasonException(value);
        }
    }
}