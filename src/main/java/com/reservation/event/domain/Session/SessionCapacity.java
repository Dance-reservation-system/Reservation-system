package com.reservation.event.domain.Session;

import com.reservation.event.domain.Session.exception.InvalidSessionCapacityException;

record SessionCapacity(int value) {
    public SessionCapacity {
        if (value <= 0) {
            throw new InvalidSessionCapacityException(value);
        }
    }
}
