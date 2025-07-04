package com.reservation.instructor.domain;

import java.util.Objects;
import java.util.UUID;

record InstructorId(UUID value) {
    public InstructorId {
        Objects.requireNonNull(value);
    }

    public static InstructorId next() {
        return new InstructorId(UUID.randomUUID());
    }
}