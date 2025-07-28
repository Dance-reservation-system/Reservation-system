package com.reservation.studio.domain;

import java.time.LocalDateTime;

record BusinessHoursChangedEvent(StudioId studioId, BusinessHours businessHours,
                                 LocalDateTime changedAt) implements StudioEvent {
    BusinessHoursChangedEvent(StudioId studioId, BusinessHours businessHours) {
        this(studioId, businessHours, LocalDateTime.now());
    }
}
