package com.reservation.studio.domain;

import java.time.LocalDateTime;

record StudioActivatedEvent(StudioId studioId, LocalDateTime activatedAt) implements StudioEvent {
    StudioActivatedEvent(StudioId studioId) {
        this(studioId, LocalDateTime.now());
    }
}
