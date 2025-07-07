package com.reservation.event.domain.session;

import com.reservation.event.domain.session.exception.InvalidSessionCapacityException;

record SessionCapacity(int value) {
    public SessionCapacity {
        if (value <= 0) {
            throw new InvalidSessionCapacityException(value);
        }
    }
}
