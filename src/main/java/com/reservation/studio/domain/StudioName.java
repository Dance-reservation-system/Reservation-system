package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.InvalidStudioNameException;

import java.util.Objects;

record StudioName(String value) {
    StudioName {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new InvalidStudioNameException("blank");
        }
        if (value.length() > 100) {
            throw new InvalidStudioNameException("longer than 100 characters");
        }
    }
}
