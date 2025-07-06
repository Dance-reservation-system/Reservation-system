package com.reservation.event.domain.Session;

import com.reservation.common.AggregateRoot;
import com.reservation.event.domain.Session.exception.ReservationsExistException;
import com.reservation.event.domain.Session.exception.SessionAlreadyCancelledException;
import com.reservation.event.domain.Session.exception.SessionUpdatedException;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Session implements AggregateRoot<SessionEvent> {

    @EqualsAndHashCode.Include
    private final SessionId sessionId;
    private InstructorId instructorId;
    private HallId hallId;
    private SessionTitle title;
    private SessionCapacity capacity;
    private RecurrencePattern recurrence;
    private SessionStatus status;
    private boolean reservation;

    private final ArrayList<SessionEvent> sessionEvents = new ArrayList<>();

    Session(SessionId sessionId, InstructorId instructorId, HallId hallId, SessionTitle title, SessionCapacity capacity, RecurrencePattern recurrence) {
        this.sessionId = Objects.requireNonNull(sessionId);
        this.instructorId = Objects.requireNonNull(instructorId);
        this.hallId = Objects.requireNonNull(hallId);
        this.title = Objects.requireNonNull(title);
        this.capacity = Objects.requireNonNull(capacity);
        this.recurrence = Objects.requireNonNull(recurrence);
        this.status = SessionStatus.SCHEDULED;
        this.reservation = false;
    }

    public static Session create(SessionId sessionId, InstructorId instructorId, HallId hallId, SessionTitle title, SessionCapacity capacity, RecurrencePattern recurrence) {
        Session session = new Session(sessionId, instructorId, hallId, title, capacity, recurrence);
        session.registerEvent(new SessionCreated(sessionId));
        return session;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public InstructorId getInstructorId() {
        return instructorId;
    }

    public HallId getHallId() {
        return hallId;
    }

    public void updateScheduling(HallId newHallId, SessionCapacity newCapacity, RecurrencePattern newRecurrence) {
        if (!isScheduled()) {
            throw new SessionUpdatedException();
        }
        this.hallId = Objects.requireNonNull(newHallId);
        this.capacity = Objects.requireNonNull(newCapacity);
        this.recurrence = Objects.requireNonNull(newRecurrence);

        registerEvent(new SessionRescheduled(sessionId));
    }

    public void updateMetadata(InstructorId instructorId, SessionTitle newTitle) {
        if (!isScheduled()) {
            throw new SessionUpdatedException();
        }
        this.instructorId = Objects.requireNonNull(instructorId);
        this.title = Objects.requireNonNull(newTitle);

        registerEvent(new SessionUpdatedMetadata(sessionId));
    }

    public void cancel() {
        if (hasReservation()) {
            throw new ReservationsExistException();
        }
        if (!isScheduled()) {
            throw new SessionAlreadyCancelledException();
        }
        this.status = SessionStatus.CANCELLED;

        registerEvent(new SessionCancelled(sessionId));
    }

    public List<SessionOccurrenceDraft> generateOccurrenceDrafts() {
        return recurrence.generateOccurrences(this.sessionId);
    }

    @Override
    public List<SessionEvent> pullEvents() {
        List<SessionEvent> copyEvents = List.copyOf(sessionEvents);
        sessionEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(SessionEvent event) {
        sessionEvents.add(Objects.requireNonNull(event));
    }

    public boolean hasTitle(SessionTitle title) {
        return Objects.equals(this.title, title);
    }

    public boolean hasCapacity(SessionCapacity capacity) {
        return Objects.equals(this.capacity, capacity);
    }

    public boolean hasReservation() {
        return reservation;
    }

    public boolean isScheduled() {
        return status.isScheduled();
    }
}
