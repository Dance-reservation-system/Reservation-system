package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidBioException;

record Bio(String value) {
    public Bio {
        if (value == null) {
            throw new InvalidBioException("Bio value cannot be null");
        }
        if (value.length() > 256) {
            throw new InvalidBioException("Bio value cannot be longer than 256 characters");
        }
    }
}
