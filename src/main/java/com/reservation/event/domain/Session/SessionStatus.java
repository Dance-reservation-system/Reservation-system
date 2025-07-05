package com.reservation.event.domain.Session;

enum SessionStatus {
    SCHEDULED,
    CANCELLED;

    public boolean isScheduled() {
        return this == SCHEDULED;
    }
}
