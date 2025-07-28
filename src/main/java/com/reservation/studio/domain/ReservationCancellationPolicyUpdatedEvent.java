package com.reservation.studio.domain;

import java.time.LocalDateTime;

record ReservationCancellationPolicyUpdatedEvent(StudioId studioId,
                                                 ReservationCancellationPolicy reservationCancellationPolicy,
                                                 LocalDateTime updatedAt) implements StudioEvent {
    ReservationCancellationPolicyUpdatedEvent(StudioId studioId, ReservationCancellationPolicy reservationCancellationPolicy) {
        this(studioId, reservationCancellationPolicy, LocalDateTime.now());
    }
}
