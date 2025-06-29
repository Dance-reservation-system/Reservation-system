package com.reservation.instructor.domain;

import java.time.Instant;

record InstructorActivated(InstructorId instructorId, SystemUserId systemUserId,
                                  Instant activationAt) implements InstructorEvent {
    public InstructorActivated(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, Instant.now());
    }
}
