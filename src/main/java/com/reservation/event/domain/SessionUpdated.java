package com.reservation.event.domain;

import java.time.Instant;

record SessionUpdated(SessionId sessionId, Instant updatedAt) implements SessionEvent {
    public SessionUpdated(SessionId sessionId) {
        this(sessionId, Instant.now());
    }
}
