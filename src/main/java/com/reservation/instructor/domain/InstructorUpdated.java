package com.reservation.instructor.domain;

import java.time.Instant;

record InstructorUpdated(InstructorId instructorId, SystemUserId systemUserId,
                         Instant updatedAt) implements InstructorEvent {
    public InstructorUpdated(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, Instant.now());
    }
}