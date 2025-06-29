package com.reservation.event.domain;

import java.time.Instant;

public record SessionOccurrenceCancelled(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
