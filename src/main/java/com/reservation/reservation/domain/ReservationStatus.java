package com.reservation.reservation.domain;

enum ReservationStatus {
    ACTIVE,
    CANCELLED_EARLY,
    CANCELLED_LATE;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
