package com.reservation.event.domain.SessionOccurrence;

import java.time.Instant;

record SessionOccurrenceCompleted( SessionOccurrenceId id, Instant createdAt)implements SessionOccurrenceEvent {
}
