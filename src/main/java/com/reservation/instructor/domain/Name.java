package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorNameException;

record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new InvalidInstructorNameException("Full name cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new InvalidInstructorNameException("Name cannot be longer than 100 characters");
        }
    }
}
