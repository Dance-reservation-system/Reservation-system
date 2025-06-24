package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallNameException;

record HallName(String value) {

    public HallName {
        if (value.isBlank()) {
            throw new InvalidHallNameException(value, "It must not be blank.");
        }
        if(value.length() > 100) {
            throw new InvalidHallNameException(value, "It must not exceed 100 characters.");
        }
    }
}
