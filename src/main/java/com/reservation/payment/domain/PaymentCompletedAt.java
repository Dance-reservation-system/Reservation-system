package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidPaymentCompletionTimeException;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentCompletedAt(LocalDateTime value) {

    public PaymentCompletedAt {
        Objects.requireNonNull(value, "Payment completion time must not be null");
        if (value.isAfter(LocalDateTime.now())) {
            throw new InvalidPaymentCompletionTimeException(LocalDateTime.now());
        }
    }

    public static PaymentCompletedAt now() {
        return new PaymentCompletedAt(LocalDateTime.now());
    }
}