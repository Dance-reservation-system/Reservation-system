package com.reservation.studio.domain;

import java.time.LocalDateTime;

record StudioCreatedEvent(StudioId studioId, OwnerId ownerId, LocalDateTime registeredAt) implements StudioEvent {
    StudioCreatedEvent(StudioId studioId, OwnerId ownerId) {
        this(studioId, ownerId, LocalDateTime.now());
    }
}
