package com.reservation.event.domain;

public sealed interface SessionEvent permits SessionCancelled, SessionCreated, SessionUpdated {
}
