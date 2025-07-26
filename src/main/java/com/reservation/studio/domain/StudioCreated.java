package com.reservation.studio.domain;

import java.time.Instant;

record StudioCreated(StudioId studioId, OwnerId ownerId, Instant registeredAt) implements StudioEvent {
    StudioCreated(StudioId studioId, OwnerId ownerId) {
        this(studioId, ownerId, Instant.now());
    }
}
