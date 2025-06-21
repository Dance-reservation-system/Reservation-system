package com.reservation.attendance.domain;

import java.util.Objects;
import java.util.UUID;

record ClientId(UUID value) {
    public ClientId {
        Objects.requireNonNull(value);
    }
}
