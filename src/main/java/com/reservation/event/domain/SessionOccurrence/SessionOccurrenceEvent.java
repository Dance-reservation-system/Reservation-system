package com.reservation.event.domain.SessionOccurrence;

public sealed interface SessionOccurrenceEvent permits SessionOccurrenceCancelled,
        SessionOccurrenceCompleted, SessionOccurrenceCreated {
}
