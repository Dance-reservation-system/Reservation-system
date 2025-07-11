package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;

import java.time.Instant;

record SessionCancelled(SessionId sessionId, Instant cancelledAt) implements SessionEvent {
    public SessionCancelled(SessionId sessionId){
        this(sessionId, Instant.now());
    }
}
