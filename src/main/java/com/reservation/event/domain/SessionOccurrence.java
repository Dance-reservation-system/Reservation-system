package com.reservation.event.domain;

import com.reservation.event.domain.exception.SessionOccurrenceAlreadyCanceledException;
import com.reservation.event.domain.exception.SessionOccurrenceNotStartedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SessionOccurrence {
    @Getter
    @EqualsAndHashCode.Include
    private final SessionOccurrenceId id;
    @Getter
    private final SessionId sessionId;
    private final LocalDateTime startDateTime;
    private final Duration duration;
    private SessionOccurrenceStatus status;

    private SessionOccurrence(SessionOccurrenceId id, SessionId sessionId,
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


    public void cancel() {
        if (isCanceled()) {
            throw new SessionOccurrenceAlreadyCanceledException();
        }
        if (isScheduled()) {
            status = SessionOccurrenceStatus.CANCELLED;
        }
    }

    public void complete() {
        if (startDateTime.isAfter(LocalDateTime.now())) {
            throw new SessionOccurrenceNotStartedException();
        }

        if (isScheduled()) {
            status = SessionOccurrenceStatus.COMPLETED;
        }
    }

    public boolean isScheduled() {
        return this.status == SessionOccurrenceStatus.SCHEDULED;
    }

    public boolean isCanceled() {
        return this.status == SessionOccurrenceStatus.CANCELLED;
    }

    public boolean hasStarted() {
        return !startDateTime.isAfter(LocalDateTime.now());
    }

    public boolean isLongerThan(Duration otherDuration) {
        return this.duration.compareTo(otherDuration) > 0;
    }

    public boolean isCompleted() {
        return this.status == SessionOccurrenceStatus.COMPLETED;
    }
}
