package com.reservation.instructor.domain;

import java.time.LocalDateTime;

record InstructorActivatedEvent(InstructorId instructorId,
                                SystemUserId systemUserId,
                                LocalDateTime activatedAt) implements InstructorEvent {
    InstructorActivatedEvent(InstructorId instructorId, SystemUserId systemUserId) {
        this(instructorId, systemUserId, LocalDateTime.now());
    }
}
