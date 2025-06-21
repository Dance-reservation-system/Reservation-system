package com.reservation.attendance.domain;

import java.util.Objects;
import java.util.UUID;

record AttendanceId(UUID value) {
    public AttendanceId {
        Objects.requireNonNull(value);
    }
}
