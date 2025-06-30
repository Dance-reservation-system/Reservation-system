package com.reservation.event.domain;

sealed interface SessionOccurrenceEvent permits SessionOccurrenceCancelled,
        SessionOccurrenceCompleted, SessionOccurrenceCreated{
}
