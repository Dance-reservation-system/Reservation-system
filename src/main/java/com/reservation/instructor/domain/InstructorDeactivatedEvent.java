package com.reservation.instructor.domain;

import java.time.LocalDateTime;

record InstructorDeactivatedEvent(InstructorId instructorId,
                                  SystemUserId systemUserId,
                                  LocalDateTime deactivatedAt) implements InstructorEvent {
    InstructorDeactivatedEvent(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, LocalDateTime.now());
    }
}
