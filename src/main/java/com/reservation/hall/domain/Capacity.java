package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallCapacityException;

record Capacity(int value) implements Comparable<Capacity> {

    public Capacity {
        if (value <= 0) {
            throw new InvalidHallCapacityException(value);
        }
    }

    @Override
    public int compareTo(Capacity other) {
        return Integer.compare(this.value, other.value);
    }

    public boolean isGreaterThanOrEqual(Capacity other) {
        return this.compareTo(other) >= 0;
    }
}
