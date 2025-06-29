package com.reservation.event.domain;

import java.time.Instant;

public record SessionOccurrenceCreated(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent{
}
