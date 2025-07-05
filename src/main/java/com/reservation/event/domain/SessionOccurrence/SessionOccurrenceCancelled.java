package com.reservation.event.domain.SessionOccurrence;

import java.time.Instant;

record SessionOccurrenceCancelled(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
