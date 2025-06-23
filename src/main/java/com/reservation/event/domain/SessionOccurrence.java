package com.reservation.event.domain;

import com.reservation.event.domain.exception.SessionOccurrenceAlreadyCanceledException;
import com.reservation.event.domain.exception.SessionOccurrenceNotStartedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SessionOccurrence {
    @EqualsAndHashCode.Include
    private final SessionOccurrenceId id;
    private final SessionId sessionId;
    private final LocalDateTime startDateTime;
    private final Duration duration;
    private SessionOccurrenceStatus status;

    SessionOccurrence(SessionOccurrenceId id, SessionId sessionId,
                      LocalDateTime startDateTime, Duration duration
                      ) {
        this.id = Objects.requireNonNull(id);
        this.sessionId = Objects.requireNonNull(sessionId);
        this.startDateTime = Objects.requireNonNull(startDateTime);
        this.duration = Objects.requireNonNull(duration);
        this.status = SessionOccurrenceStatus.SCHEDULED;
    }

    public static SessionOccurrence create(
            SessionOccurrenceId id,
            SessionId sessionId,
            LocalDateTime startDateTime,
            Duration duration) {
        return new SessionOccurrence(id, sessionId, startDateTime, duration);
    }

    public void cancel(){
        if (status == SessionOccurrenceStatus.CANCELLED){
            throw new SessionOccurrenceAlreadyCanceledException();
        }
        if (status == SessionOccurrenceStatus.SCHEDULED) {
            status = SessionOccurrenceStatus.CANCELLED;
        }
    }

    public void complete(){
        if (startDateTime.isAfter(LocalDateTime.now())) {
            throw new SessionOccurrenceNotStartedException();
        }

        if (status == SessionOccurrenceStatus.SCHEDULED) {
            status = SessionOccurrenceStatus.COMPLETED;
        }
    }

    public boolean isActive() {
        return this.status == SessionOccurrenceStatus.SCHEDULED;
    }
}
