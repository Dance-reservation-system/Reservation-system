package com.reservation.payment.domain;

import java.util.Objects;
import java.util.UUID;

record PaymentId(UUID value) {
    PaymentId {
        Objects.requireNonNull(value);
    }

    static PaymentId next() {
        return new PaymentId(UUID.randomUUID());
    }
}