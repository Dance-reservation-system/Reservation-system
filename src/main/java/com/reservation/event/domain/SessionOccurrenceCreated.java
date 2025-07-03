package com.reservation.event.domain;

import java.time.Instant;

record SessionOccurrenceCreated(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
