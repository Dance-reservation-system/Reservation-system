package com.reservation.event.domain.SessionOccurrence;

import java.time.Instant;

record SessionOccurrenceCreated(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
