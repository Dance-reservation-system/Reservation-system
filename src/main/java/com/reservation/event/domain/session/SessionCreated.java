package com.reservation.event.domain.session;


import com.reservation.event.domain.SessionId;

import java.time.Instant;

record SessionCreated(SessionId sessionId, Instant createdAt) implements SessionEvent {
    public SessionCreated (SessionId sessionId){
        this(sessionId, Instant.now());
    }
}
