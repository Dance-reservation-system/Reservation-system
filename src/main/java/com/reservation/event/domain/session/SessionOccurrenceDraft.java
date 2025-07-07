package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;

import java.time.Duration;
import java.time.LocalDateTime;

public record SessionOccurrenceDraft(
        SessionId sessionId,
        LocalDateTime startDateTime,
        Duration duration
) {
}
