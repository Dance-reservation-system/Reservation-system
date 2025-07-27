package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorSpecialtyException;

record InstructorSpecialty(String value) {
    InstructorSpecialty {
        if (value == null || value.isBlank()) {
            throw new InvalidInstructorSpecialtyException("Instructor specialty cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new InvalidInstructorSpecialtyException("Instructor specialty cannot be longer than 100 characters");
        }
    }
}
