package com.reservation.instructor.domain;

import java.time.Instant;

record InstructorDeactivated(InstructorId instructorId, SystemUserId systemUserId,
                                    Instant deactivatedAt) implements InstructorEvent {
    public InstructorDeactivated(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, Instant.now());
    }
}