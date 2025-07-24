package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidPaymentCompletionTimeException;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentCompletedAt(LocalDateTime value) {

    PaymentCompletedAt {
        Objects.requireNonNull(value, "Payment completion time must not be null");
        LocalDateTime now = LocalDateTime.now();
        if (value.isAfter(now)) {
            throw new InvalidPaymentCompletionTimeException(value);
        }
    }

    static PaymentCompletedAt now() {
        return new PaymentCompletedAt(LocalDateTime.now());
    }
}