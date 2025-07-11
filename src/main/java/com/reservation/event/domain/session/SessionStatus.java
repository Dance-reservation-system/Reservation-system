package com.reservation.event.domain.session;

enum SessionStatus {
    SCHEDULED,
    CANCELLED;

    public boolean isScheduled() {
        return this == SCHEDULED;
    }
}
