package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidPaymentInitiationTimeException;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentInitiatedAt(LocalDateTime value) {

    PaymentInitiatedAt {
        Objects.requireNonNull(value, "Payment initiation time must not be null");
        if (value.isAfter(LocalDateTime.now())) {
            throw new InvalidPaymentInitiationTimeException(value);
        }
    }

    boolean isBefore(PaymentInitiatedAt other) {
        return value.isBefore(other.value());
    }

    static PaymentInitiatedAt now() {
        return new PaymentInitiatedAt(LocalDateTime.now());
    }
}