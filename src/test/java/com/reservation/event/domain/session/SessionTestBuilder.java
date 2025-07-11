package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

class SessionTestBuilder {

    private SessionId sessionId = SessionId.next();
    private InstructorId instructorId = InstructorId.next();
    private HallId hallId = HallId.next();
    private SessionTitle title = new SessionTitle("Title");
    private SessionCapacity capacity = new SessionCapacity(10);
    private RecurrencePattern recurrencePattern;

    public SessionTestBuilder() {
        var duration = Duration.ofHours(1);
        this.recurrencePattern = new RecurrencePattern(
            Set.of(new RecurrenceSlot(DayOfWeek.MONDAY, LocalTime.of(10, 0))),
            duration,
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 31),
            1
        );
    }

    public SessionTestBuilder withTitle(String title) {
        this.title = new SessionTitle(title);
        return this;
    }

    public SessionTestBuilder withCapacity(int value) {
        this.capacity = new SessionCapacity(value);
        return this;
    }

    public SessionTestBuilder withRecurrencePattern(RecurrencePattern pattern) {
        this.recurrencePattern = pattern;
        return this;
    }

    public Session build() {
        return Session.create(sessionId, instructorId, hallId, title, capacity, recurrencePattern);
    }
}
