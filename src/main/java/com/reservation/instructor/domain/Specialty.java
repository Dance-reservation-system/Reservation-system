package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidSpecialityException;

record Specialty(String value) {
    public Specialty {
        if (value == null || value.isBlank()) {
            throw new InvalidSpecialityException("Specialty value cannot be null or empty");
        }
        if (value.length() > 100) {
            throw new InvalidSpecialityException("Specialty value cannot be longer than 100 characters");
        }
    }
}
