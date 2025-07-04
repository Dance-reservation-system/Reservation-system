package com.reservation.event.domain;

import java.time.Instant;

record SessionOccurrenceCompleted( SessionOccurrenceId id, Instant createdAt)implements SessionOccurrenceEvent {
}
