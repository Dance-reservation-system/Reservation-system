package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidPaymentFailureTimeException;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentFailedAt(LocalDateTime value) {

    PaymentFailedAt {
        Objects.requireNonNull(value, "Failure time must not be null");

        LocalDateTime now = LocalDateTime.now();
        if (value.isAfter(now)) {
            throw new InvalidPaymentFailureTimeException(value);
        }
    }

    static PaymentFailedAt now() {
        return new PaymentFailedAt(LocalDateTime.now());
    }
}