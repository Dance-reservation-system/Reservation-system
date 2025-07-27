package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorNameException;

record InstructorName(String value) {
    InstructorName {
        if (value == null || value.isBlank()) {
            throw new InvalidInstructorNameException("Instructor name cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new InvalidInstructorNameException("Instructor name cannot be longer than 100 characters");
        }
    }
}
