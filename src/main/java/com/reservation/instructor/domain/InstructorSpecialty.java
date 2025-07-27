package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorSpecialityException;

record InstructorSpecialty(String value) {
    InstructorSpecialty {
        if (value == null || value.isBlank()) {
            throw new InvalidInstructorSpecialityException("Instructor specialty cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new InvalidInstructorSpecialityException("Instructor specialty cannot be longer than 100 characters");
        }
    }
}
