package com.reservation.event.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class SessionOccurrence {
    SessionOccurrenceId id;
    SessionId sessionId;
    LocalDateTime startDateTime;
    Duration duration;
    SessionOccurrenceStatus status;
}
