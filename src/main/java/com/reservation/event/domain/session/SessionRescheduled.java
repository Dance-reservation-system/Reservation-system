package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;

import java.time.Instant;

record SessionRescheduled(SessionId sessionId, Instant updatedAt) implements SessionEvent {
    public SessionRescheduled(SessionId sessionId) {
        this(sessionId, Instant.now());
    }
}
