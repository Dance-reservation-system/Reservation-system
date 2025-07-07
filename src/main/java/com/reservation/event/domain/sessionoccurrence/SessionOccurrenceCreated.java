package com.reservation.event.domain.sessionoccurrence;

import java.time.Instant;

record SessionOccurrenceCreated(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
