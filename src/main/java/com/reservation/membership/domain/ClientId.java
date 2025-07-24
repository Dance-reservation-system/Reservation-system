package com.reservation.membership.domain;

import java.util.Objects;
import java.util.UUID;

record ClientId(UUID value) {
    ClientId {
        Objects.requireNonNull(value);
    }

    static ClientId next() {
        return new ClientId(UUID.randomUUID());
    }
}
