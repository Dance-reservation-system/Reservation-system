package com.reservation.studio.domain;

import java.time.LocalDateTime;

record ContactDetailsUpdatedEvent(StudioId studioId, ContactDetails contactDetails,
                                  LocalDateTime updatedAt) implements StudioEvent {
    ContactDetailsUpdatedEvent(StudioId studioId, ContactDetails contactDetails) {
        this(studioId, contactDetails, LocalDateTime.now());
    }
}
