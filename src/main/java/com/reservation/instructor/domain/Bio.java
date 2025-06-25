package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.BioCannotBeNullException;

record Bio(String value) {
    public Bio {
        if (value == null) {
            throw new BioCannotBeNullException();
        }
    }
}
