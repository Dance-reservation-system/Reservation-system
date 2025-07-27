package com.reservation.instructor.domain;

enum InstructorStatus {
    ACTIVE,
    INACTIVE;

    boolean isActive() {
        return this == ACTIVE;
    }
}
