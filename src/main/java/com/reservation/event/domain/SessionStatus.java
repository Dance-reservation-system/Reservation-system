package com.reservation.event.domain;

enum SessionStatus {
    SCHEDULED,
    CANCELLED;

    public boolean isScheduled() {
        return this == SCHEDULED;
    }
}
