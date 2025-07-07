package com.reservation.event.domain.sessionoccurrence;

import java.time.Instant;

record SessionOccurrenceCompleted( SessionOccurrenceId id, Instant createdAt)implements SessionOccurrenceEvent {
}
