package com.reservation.studio.domain;

import java.time.Instant;

record CancellationPolicyUpdated(StudioId studioId, CancellationPolicy cancellationPolicy,
                                 Instant updatedAt) implements StudioEvent {
    CancellationPolicyUpdated(StudioId studioId, CancellationPolicy cancellationPolicy) {
        this(studioId, cancellationPolicy, Instant.now());
    }
}
