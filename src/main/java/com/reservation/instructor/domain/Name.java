package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorNameCannotBeBlankException;

record Name(String fullName) {
    public Name {
        if (fullName == null || fullName.isBlank()) {
            throw new InstructorNameCannotBeBlankException();
        }
    }
}
