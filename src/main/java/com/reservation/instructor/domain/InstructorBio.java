package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorBioException;

record InstructorBio(String value) {

    InstructorBio {
        if (value == null || value.isBlank()) {
            throw new InvalidInstructorBioException("Instructor bio cannot be null or blank");
        }
        if (value.length() > 256) {
            throw new InvalidInstructorBioException("Instructor bio cannot be longer than 256 characters");
        }
    }
}
