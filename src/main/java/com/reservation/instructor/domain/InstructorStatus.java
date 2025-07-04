package com.reservation.instructor.domain;

enum InstructorStatus {
    ACTIVE,
    INACTIVE;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
