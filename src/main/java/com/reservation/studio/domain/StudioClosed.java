package com.reservation.studio.domain;

import java.time.Instant;

record StudioClosed(StudioId studioId, Instant closedAt) implements StudioEvent {
    StudioClosed(StudioId studioId) {
        this(studioId, Instant.now());
    }
}
