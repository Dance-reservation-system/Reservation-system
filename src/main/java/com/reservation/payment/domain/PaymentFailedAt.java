package com.reservation.payment.domain;

import java.time.LocalDateTime;
import java.util.Objects;

record PaymentFailedAt(LocalDateTime value) {
    public PaymentFailedAt {
        Objects.requireNonNull(value, "failedAt must not be null");
    }
}
