package com.reservation.studio.domain;

import java.time.Instant;

record StudioActivated(StudioId studioId, Instant activatedAt) implements StudioEvent {
    StudioActivated(StudioId studioId) {
        this(studioId, Instant.now());
    }
}
