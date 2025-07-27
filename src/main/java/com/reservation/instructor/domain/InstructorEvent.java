package com.reservation.instructor.domain;

sealed interface InstructorEvent permits InstructorActivatedEvent, InstructorDeactivatedEvent, InstructorCreatedEvent, InstructorUpdatedEvent {
}
