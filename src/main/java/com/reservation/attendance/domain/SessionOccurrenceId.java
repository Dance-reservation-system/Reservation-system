package com.reservation.attendance.domain;

import java.util.Objects;
import java.util.UUID;

record SessionOccurrenceId(UUID value) {
    public SessionOccurrenceId {
        Objects.requireNonNull(value);
    }
}
