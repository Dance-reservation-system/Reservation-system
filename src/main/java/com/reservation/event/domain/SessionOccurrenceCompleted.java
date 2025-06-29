package com.reservation.event.domain;

import java.time.Instant;

public record SessionOccurrenceCompleted(SessionOccurrenceId id, Instant createdAt)implements SessionOccurrenceEvent {
}
