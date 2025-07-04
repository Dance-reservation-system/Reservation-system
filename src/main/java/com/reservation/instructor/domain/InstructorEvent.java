package com.reservation.instructor.domain;

public sealed interface InstructorEvent permits InstructorActivated, InstructorDeactivated, InstructorCreated, InstructorUpdated {
}
