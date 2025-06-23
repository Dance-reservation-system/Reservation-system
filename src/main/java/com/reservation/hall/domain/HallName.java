package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallNameException;

record HallName(String value) {

    public HallName {
        if (value.isBlank()) {
            throw new InvalidHallNameException(value);
        }
    }
}
