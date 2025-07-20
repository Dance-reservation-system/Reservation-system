package com.reservation.payment.domain;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentInitiatedAt(LocalDateTime value) {
    public PaymentInitiatedAt {
        Objects.requireNonNull(value);
    }

    public boolean isBeforeNow() {
        return value.isBefore(LocalDateTime.now());
    }
}
