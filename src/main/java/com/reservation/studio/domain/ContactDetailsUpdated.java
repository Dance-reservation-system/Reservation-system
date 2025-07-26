package com.reservation.studio.domain;

import java.time.Instant;

record ContactDetailsUpdated(StudioId studioId, ContactDetails contactDetails,
                             Instant updatedAt) implements StudioEvent {
    ContactDetailsUpdated(StudioId studioId, ContactDetails contactDetails) {
        this(studioId, contactDetails, Instant.now());
    }
}
