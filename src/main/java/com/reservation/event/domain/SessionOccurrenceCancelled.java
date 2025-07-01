package com.reservation.event.domain;

import java.time.Instant;

record SessionOccurrenceCancelled(SessionOccurrenceId id, Instant createdAt) implements SessionOccurrenceEvent {
}
