package com.reservation.instructor.domain;

import java.time.LocalDateTime;

record InstructorCreatedEvent(InstructorId instructorId,
                              SystemUserId systemUserId,
                              LocalDateTime createdAt) implements InstructorEvent {
    InstructorCreatedEvent(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, LocalDateTime.now());
    }
}
