package com.reservation.event.domain.Session;

import java.time.Instant;

record SessionCancelled(SessionId sessionId, Instant cancelledAt) implements SessionEvent {
    public SessionCancelled(SessionId sessionId){
        this(sessionId, Instant.now());
    }
}
