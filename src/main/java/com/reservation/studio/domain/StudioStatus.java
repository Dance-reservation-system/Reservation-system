package com.reservation.studio.domain;

enum StudioStatus {
    INACTIVE,
    ACTIVE,
    CLOSED;

    boolean isActive() {
        return this == ACTIVE;
    }
}
