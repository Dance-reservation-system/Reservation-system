package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.SpecialtyCannotBeNullException;

record Specialty(String value) {
    public Specialty {
        if (value == null || value.isBlank()) {
            throw new SpecialtyCannotBeNullException();
        }
    }
}
