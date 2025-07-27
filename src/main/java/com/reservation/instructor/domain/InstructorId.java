package com.reservation.instructor.domain;

import java.util.Objects;
import java.util.UUID;

record InstructorId(UUID value) {
    InstructorId {
        Objects.requireNonNull(value);
    }

    static InstructorId next() {
        return new InstructorId(UUID.randomUUID());
    }
}