package com.reservation.instructor.domain;

import java.time.LocalDateTime;

record InstructorUpdatedEvent(InstructorId instructorId,
                              SystemUserId systemUserId,
                              LocalDateTime updatedAt) implements InstructorEvent {
    InstructorUpdatedEvent(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, LocalDateTime.now());
    }
}
