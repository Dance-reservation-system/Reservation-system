package com.reservation.event.domain.Session;

import java.time.Duration;
import java.time.LocalDateTime;

public record SessionOccurrenceDraft(
        SessionId sessionId,
        LocalDateTime startDateTime,
        Duration duration
) {
}
