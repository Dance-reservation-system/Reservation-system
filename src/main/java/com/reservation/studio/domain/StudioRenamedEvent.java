package com.reservation.studio.domain;

import java.time.LocalDateTime;

record StudioRenamedEvent(StudioId studioId, LocalDateTime renamedAt) implements StudioEvent {
    StudioRenamedEvent(StudioId studioId) {
        this(studioId, LocalDateTime.now());
    }
}
