package com.reservation.event.domain;

import com.reservation.event.domain.exception.InvalidSessionCapacityException;

record SessionCapacity(int value) {
    public SessionCapacity {
        if (value <= 0) {
            throw new InvalidSessionCapacityException(value);
        }
    }
}
