package com.reservation.event.domain;

public sealed interface SessionOccurrenceEvent permits SessionOccurrenceCancelled,
        SessionOccurrenceCompleted, SessionOccurrenceCreated {
}
