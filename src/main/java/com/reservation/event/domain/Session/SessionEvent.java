package com.reservation.event.domain.Session;

public sealed interface SessionEvent permits SessionCancelled, SessionCreated, SessionUpdatedMetadata, SessionRescheduled {
}
