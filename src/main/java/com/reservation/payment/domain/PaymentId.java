package com.reservation.payment.domain;

import java.util.Objects;
import java.util.UUID;

record PaymentId(UUID value) {
    public PaymentId {
        Objects.requireNonNull(value);
    }

    public static PaymentId next() {
        return new PaymentId(UUID.randomUUID());
    }
}