package com.reservation.studio.domain;

import java.time.LocalDateTime;

record StudioClosedEvent(StudioId studioId, LocalDateTime closedAt) implements StudioEvent {
    StudioClosedEvent(StudioId studioId) {
        this(studioId, LocalDateTime.now());
    }
}
