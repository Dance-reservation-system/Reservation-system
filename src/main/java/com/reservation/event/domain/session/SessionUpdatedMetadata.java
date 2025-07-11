package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;

import java.time.Instant;

record SessionUpdatedMetadata(SessionId sessionId, Instant updatedAt) implements SessionEvent {
    public SessionUpdatedMetadata(SessionId sessionId) {
        this(sessionId, Instant.now());
    }
}