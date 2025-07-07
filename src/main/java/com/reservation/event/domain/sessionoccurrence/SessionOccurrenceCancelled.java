package com.reservation.event.domain.sessionoccurrence;

import java.time.Instant;

record SessionOccurrenceCancelled(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
