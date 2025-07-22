package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidPaymentCompletionTimeException;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentCompletedAt(LocalDateTime value) {

    PaymentCompletedAt {
        Objects.requireNonNull(value, "Payment completion time must not be null");
        if (value.isAfter(LocalDateTime.now())) {
            throw new InvalidPaymentCompletionTimeException(LocalDateTime.now());
        }
    }

    static PaymentCompletedAt now() {
        return new PaymentCompletedAt(LocalDateTime.now());
    }
}