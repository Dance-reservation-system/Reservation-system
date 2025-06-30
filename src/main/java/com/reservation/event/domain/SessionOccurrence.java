package com.reservation.event.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.event.domain.events.SessionOccurrenceCancelled;
import com.reservation.event.domain.events.SessionOccurrenceCompleted;
import com.reservation.event.domain.events.SessionOccurrenceCreated;
import com.reservation.event.domain.events.SessionOccurrenceEvent;
import com.reservation.event.domain.exception.SessionOccurrenceAlreadyCanceledException;
import com.reservation.event.domain.exception.SessionOccurrenceNotStartedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SessionOccurrence implements AggregateRoot<SessionOccurrenceEvent> {
    @Getter
    @EqualsAndHashCode.Include
    private final SessionOccurrenceId id;
    @Getter
    private final SessionId sessionId;
    private final LocalDateTime startDateTime;
    private final Duration duration;
    private SessionOccurrenceStatus status;

    private final ArrayList<SessionOccurrenceEvent> sessionOccurrenceEvents = new ArrayList<>();

    private SessionOccurrence(SessionOccurrenceId id, SessionId sessionId,
                      LocalDateTime startDateTime, Duration duration
    ) {
        this.id = Objects.requireNonNull(id);
        this.sessionId = Objects.requireNonNull(sessionId);
        this.startDateTime = Objects.requireNonNull(startDateTime);
        this.duration = Objects.requireNonNull(duration);
        this.status = SessionOccurrenceStatus.SCHEDULED;
        registerEvent(new SessionOccurrenceCreated(this.id, Instant.now()));
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
            registerEvent(new SessionOccurrenceCancelled(this.id, Instant.now()));
        }
    }

    public void complete() {
        if (startDateTime.isAfter(LocalDateTime.now())) {
            throw new SessionOccurrenceNotStartedException();
        }

        if (isCanceled()) {
            throw new SessionOccurrenceAlreadyCanceledException();
        }

        if (isScheduled()) {
            status = SessionOccurrenceStatus.COMPLETED;
            registerEvent(new SessionOccurrenceCompleted(this.id, Instant.now()));
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

    @Override
    public List<SessionOccurrenceEvent> pullEvents() {
        List<SessionOccurrenceEvent> copyEvents = List.copyOf(sessionOccurrenceEvents);
        sessionOccurrenceEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(SessionOccurrenceEvent event) {
        sessionOccurrenceEvents.add(Objects.requireNonNull(event));
    }
}
