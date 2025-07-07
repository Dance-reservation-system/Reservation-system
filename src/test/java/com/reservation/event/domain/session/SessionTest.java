package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;
import com.reservation.event.domain.session.exception.InvalidSessionCapacityException;
import com.reservation.event.domain.session.exception.InvalidSessionTitleException;
import com.reservation.event.domain.session.exception.SessionAlreadyCancelledException;
import com.reservation.event.domain.session.exception.SessionUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionTest {

    private SessionId sessionId;
    private InstructorId instructorId;
    private HallId hallId;
    private SessionTitle title;
    private SessionCapacity capacity;
    private RecurrencePattern recurrencePattern;
    private Session session;
    private List<SessionEvent> events;
    private Duration duration;

    @BeforeEach
    void setUp() {
        sessionId = SessionId.next();
        instructorId = InstructorId.next();
        hallId = HallId.next();
        title = new SessionTitle("Title");
        capacity = new SessionCapacity(10);

        duration = Duration.ofHours(1);
        recurrencePattern = new RecurrencePattern(
                Set.of(
                        new RecurrenceSlot(DayOfWeek.MONDAY, LocalTime.of(19, 30)),
                        new RecurrenceSlot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 30)),
                        new RecurrenceSlot(DayOfWeek.SATURDAY, LocalTime.of(10, 0))
                ),
                duration,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 2, 28),
                1
        );

        session = Session.create(sessionId, instructorId, hallId, title, capacity, recurrencePattern);
    }

    @Test
    void shouldCreateSessionWithScheduledStatusAndEvent() {
        events = session.pullEvents();

        SessionCreated createdEvent = events.stream()
                .filter(SessionCreated.class::isInstance)
                .map(SessionCreated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected SessionCreated event"));

        assertAll(
                () -> assertTrue(session.isScheduled()),
                () -> assertFalse(session.hasReservation()),
                () -> assertEquals(sessionId, createdEvent.sessionId()),
                () -> assertNotNull(createdEvent.createdAt())
        );
    }

    @Test
    void shouldThrowWhenCreatingSessionWithEmptyTitle() {
        assertThrows(InvalidSessionTitleException.class,
                () -> Session.create(sessionId, instructorId, hallId, new SessionTitle(""), capacity, recurrencePattern));
    }

    @Test
    void shouldThrowWhenCreatingSessionWithTooLongTitle() {
        String longTitle = "A".repeat(101);

        assertThrows(InvalidSessionTitleException.class,
                () -> Session.create(sessionId, instructorId, hallId, new SessionTitle(longTitle), capacity, recurrencePattern));
    }

    @Test
    void shouldThrowWhenCreatingSessionWithNegativeCapacity() {
        assertThrows(InvalidSessionCapacityException.class,
                () -> Session.create(sessionId, instructorId, hallId, title, new SessionCapacity(-5), recurrencePattern));
    }

    @Test
    void shouldUpdateSchedulingWhenScheduled() {
        HallId newHallId = HallId.next();
        SessionCapacity newCapacity = new SessionCapacity(30);
        RecurrencePattern newRecurrence = recurrencePattern;

        session.updateScheduling(newHallId, newCapacity, newRecurrence);
        events = session.pullEvents();

        SessionRescheduled rescheduledEvent = events.stream()
                .filter(SessionRescheduled.class::isInstance)
                .map(SessionRescheduled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected SessionRescheduled event"));

        assertAll(
                () -> assertEquals(newHallId, session.getHallId()),
                () -> assertTrue(session.hasCapacity(newCapacity)),
                () -> assertEquals(sessionId, rescheduledEvent.sessionId()),
                () -> assertNotNull(rescheduledEvent.updatedAt())
        );
    }

    @Test
    void shouldThrowWhenUpdatingSchedulingWhenCancelled() {
        session.cancel();

        assertThrows(SessionUpdateException.class, () ->
                session.updateScheduling(HallId.next(), new SessionCapacity(50), recurrencePattern)
        );
    }

    @Test
    void shouldUpdateMetadataWhenScheduled() {
        InstructorId newInstructorId = InstructorId.next();
        SessionTitle newTitle = new SessionTitle("Updated Title");

        session.updateMetadata(newInstructorId, newTitle);
        events = session.pullEvents();

        SessionUpdatedMetadata updatedMetadataEvent = events.stream()
                .filter(SessionUpdatedMetadata.class::isInstance)
                .map(SessionUpdatedMetadata.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected SessionUpdatedMetadata event"));

        assertAll(
                () -> assertEquals(newInstructorId, session.getInstructorId()),
                () -> assertTrue(session.hasTitle(newTitle)),
                () -> assertEquals(sessionId, updatedMetadataEvent.sessionId()),
                () -> assertNotNull(updatedMetadataEvent.updatedAt())
        );
    }

    @Test
    void shouldThrowWhenUpdatingMetadataWhenCancelled() {
        session.cancel();

        assertThrows(SessionUpdateException.class, () ->
                session.updateMetadata(InstructorId.next(), new SessionTitle("New"))
        );
    }

    @Test
    void shouldCancelSession() {
        session.cancel();
        events = session.pullEvents();

        SessionCancelled cancelledEvent = events.stream()
                .filter(SessionCancelled.class::isInstance)
                .map(SessionCancelled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected SessionCancelled event"));

        assertAll(
                () -> assertFalse(session.isScheduled()),
                () -> assertEquals(sessionId, cancelledEvent.sessionId()),
                () -> assertNotNull(cancelledEvent.cancelledAt())
        );
    }

    @Test
    void shouldThrowWhenCancellingAlreadyCancelledSession() {
        session.cancel();

        assertThrows(SessionAlreadyCancelledException.class, session::cancel);
    }

    @Test
    void shouldGenerateOccurrenceDraftsFromRecurrencePattern() {
        List<SessionOccurrenceDraft> drafts = session.generateOccurrenceDrafts();

        assertNotNull(drafts);

        for (SessionOccurrenceDraft draft : drafts) {
            DayOfWeek draftDay = draft.startDateTime().getDayOfWeek();
            LocalTime draftTime = draft.startDateTime().toLocalTime();

            boolean matchesSlot = recurrencePattern.recurrenceSlots().stream()
                    .anyMatch(slot ->
                            slot.dayOfWeek().equals(draftDay) && slot.time().equals(draftTime)
                    );

            assertAll(
                    () -> assertEquals(sessionId, draft.sessionId()),
                    () -> assertEquals(duration, draft.duration()),
                    () -> assertTrue(matchesSlot)
            );
        }
    }
}
