package com.reservation.studio.domain;

import java.time.Instant;

record StudioRenamed(StudioId studioId, Instant renamedAt) implements StudioEvent {
    StudioRenamed(StudioId studioId) {
        this(studioId, Instant.now());
    }
}
