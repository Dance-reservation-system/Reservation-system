package com.reservation.studio.domain;

import java.util.Objects;
import java.util.UUID;

record OwnerId(UUID value) {
    OwnerId {
        Objects.requireNonNull(value);
    }

    static OwnerId next() {
        return new OwnerId(UUID.randomUUID());
    }
}
