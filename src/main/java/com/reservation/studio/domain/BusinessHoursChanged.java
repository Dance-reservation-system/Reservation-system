package com.reservation.studio.domain;

import java.time.Instant;

record BusinessHoursChanged(StudioId studioId, BusinessHours businessHours, Instant changedAt) implements StudioEvent {
    BusinessHoursChanged(StudioId studioId, BusinessHours businessHours) {
        this(studioId, businessHours, Instant.now());
    }
}
