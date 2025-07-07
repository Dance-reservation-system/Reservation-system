package com.reservation.event.domain.sessionoccurrence;

public sealed interface SessionOccurrenceEvent permits SessionOccurrenceCancelled,
        SessionOccurrenceCompleted, SessionOccurrenceCreated {
}
