package com.reservation.event.domain;

import java.time.Instant;

record SessionCreated(SessionId id, Instant createdAt) implements SessionEvent{
    public SessionCreated (SessionId sessionId){
        this(sessionId, Instant.now());
    }
}
