package com.reservation.instructor.domain;

import java.time.Instant;

record InstructorCreated(InstructorId instructorId, SystemUserId systemUserId,
                                Instant activationDate) implements InstructorEvent {
    public InstructorCreated(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, Instant.now());
    }
}
