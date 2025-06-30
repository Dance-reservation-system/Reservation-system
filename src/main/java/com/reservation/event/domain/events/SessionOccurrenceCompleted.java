package com.reservation.event.domain.events;

import com.reservation.event.domain.SessionOccurrenceId;

import java.time.Instant;

public record SessionOccurrenceCompleted(SessionOccurrenceId id, Instant createdAt)implements SessionOccurrenceEvent {
}
