package com.reservation.event.domain.session;

public sealed interface SessionEvent permits SessionCancelled, SessionCreated, SessionUpdatedMetadata, SessionRescheduled {
}
