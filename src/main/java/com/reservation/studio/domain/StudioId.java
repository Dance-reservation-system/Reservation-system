package com.reservation.studio.domain;

import java.util.Objects;
import java.util.UUID;

record StudioId(UUID value) {
    StudioId {
        Objects.requireNonNull(value);
    }

    static StudioId next() {
        return new StudioId(UUID.randomUUID());
    }
}
