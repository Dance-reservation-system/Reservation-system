package com.reservation.payment.domain;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentCompletedAt(LocalDateTime value) {
    public PaymentCompletedAt {
        Objects.requireNonNull(value, "completedAt must not be null");
    }
}
