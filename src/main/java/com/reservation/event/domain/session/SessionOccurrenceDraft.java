package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;
import com.reservation.event.domain.session.exception.NonPositiveDurationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public record SessionOccurrenceDraft(
        SessionId sessionId,
        LocalDateTime startDateTime,
        Duration duration
) {
    public SessionOccurrenceDraft {
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(duration);
        if(duration.isNegative() ||  duration.isZero()) {
            throw new NonPositiveDurationException();
        }
    }
}
