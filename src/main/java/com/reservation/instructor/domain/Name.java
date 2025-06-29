package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorNameException;

record Name(String fullName) {
    public Name {
        if (fullName == null || fullName.isBlank()) {
            throw new InvalidInstructorNameException("Full name cannot be null or blank");
        }
        if (fullName.length() > 100) {
            throw new InvalidInstructorNameException("Name cannot be longer than 100 characters");
        }
    }
}
