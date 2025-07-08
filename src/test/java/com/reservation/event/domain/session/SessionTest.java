package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;
import com.reservation.event.domain.session.exception.InvalidSessionCapacityException;
import com.reservation.event.domain.session.exception.InvalidSessionTitleException;
import com.reservation.event.domain.session.exception.SessionAlreadyCancelledException;
import com.reservation.event.domain.session.exception.SessionUpdateException;
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

    private List<SessionEvent> events;

    @Test
    void shouldCreateSessionWithScheduledStatusAndEvent() {
        Session session = new SessionTestBuilder().build();

        events = session.pullEvents();

        SessionCreated createdEvent = events.stream()
                .filter(SessionCreated.class::isInstance)
                .map(SessionCreated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected SessionCreated event"));

        assertAll(
                () -> assertTrue(session.isScheduled()),
                () -> assertFalse(session.hasReservation()),
                () -> assertEquals(session.getSessionId(), createdEvent.sessionId()),
                () -> assertNotNull(createdEvent.createdAt())
        );
    }

    @Test
    void shouldThrowWhenCreatingSessionWithEmptyTitle() {
        assertThrows(InvalidSessionTitleException.class,
                () -> new SessionTestBuilder()
                        .withTitle("")
                        .build()
        );
    }

    @Test
    void shouldThrowWhenCreatingSessionWithTooLongTitle() {
        String longTitle = "A".repeat(101);

        assertThrows(InvalidSessionTitleException.class,
                () -> new SessionTestBuilder()
                        .withTitle(longTitle)
                        .build()
        );
    }

    @Test
    void shouldThrowWhenCreatingSessionWithNegativeCapacity() {
        assertThrows(InvalidSessionCapacityException.class,
                () -> new SessionTestBuilder()
                        .withCapacity(-5)
                        .build()
        );
    }
    @Test
    void shouldUpdateSchedulingWhenScheduled() {
        Session session = new SessionTestBuilder().build();
        HallId newHallId = HallId.next();
        SessionCapacity newCapacity = new SessionCapacity(30);
        RecurrencePattern newRecurrence = new RecurrencePattern(
                Set.of(new RecurrenceSlot(DayOfWeek.FRIDAY, LocalTime.of(18, 0))),
                Duration.ofHours(1),
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                1
        );

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
                () -> assertEquals(session.getSessionId(), rescheduledEvent.sessionId()),
                () -> assertNotNull(rescheduledEvent.updatedAt())
        );
    }

    @Test
    void shouldThrowWhenUpdatingSchedulingWhenCancelled() {
        Session session = new SessionTestBuilder().build();
        session.cancel();

        RecurrencePattern newRecurrence = new RecurrencePattern(
                Set.of(new RecurrenceSlot(DayOfWeek.FRIDAY, LocalTime.of(18, 0))),
                Duration.ofHours(1),
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                1
        );

        assertThrows(SessionUpdateException.class, () ->
                session.updateScheduling(HallId.next(), new SessionCapacity(50), newRecurrence)
        );
    }

    @Test
    void shouldUpdateMetadataWhenScheduled() {
        Session session = new SessionTestBuilder().build();
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
                () -> assertEquals(session.getSessionId(), updatedMetadataEvent.sessionId()),
                () -> assertNotNull(updatedMetadataEvent.updatedAt())
        );
    }

    @Test
    void shouldThrowWhenUpdatingMetadataWhenCancelled() {
        Session session = new SessionTestBuilder().build();
        session.cancel();

        assertThrows(SessionUpdateException.class, () ->
                session.updateMetadata(InstructorId.next(), new SessionTitle("New"))
        );
    }

    @Test
    void shouldCancelSession() {
        Session session = new SessionTestBuilder().build();

        session.cancel();
        events = session.pullEvents();

        SessionCancelled cancelledEvent = events.stream()
                .filter(SessionCancelled.class::isInstance)
                .map(SessionCancelled.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected SessionCancelled event"));

        assertAll(
                () -> assertFalse(session.isScheduled()),
                () -> assertEquals(session.getSessionId(), cancelledEvent.sessionId()),
                () -> assertNotNull(cancelledEvent.cancelledAt())
        );
    }

    @Test
    void shouldThrowWhenCancellingAlreadyCancelledSession() {
        Session session = new SessionTestBuilder().build();
        session.cancel();

        assertThrows(SessionAlreadyCancelledException.class, session::cancel);
    }

    @Test
    void shouldGenerateOccurrenceDraftsFromRecurrencePattern() {
        Duration duration = Duration.ofHours(1);
        RecurrencePattern pattern = new RecurrencePattern(
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

        Session session = new SessionTestBuilder()
                .withRecurrencePattern(pattern)
                .build();

        List<SessionOccurrenceDraft> drafts = session.generateOccurrenceDrafts();

        assertNotNull(drafts);

        Set<RecurrenceSlot> slots = pattern.recurrenceSlots();
        SessionId sessionId = session.getSessionId();

        for (SessionOccurrenceDraft draft : drafts) {
            DayOfWeek draftDay = draft.startDateTime().getDayOfWeek();
            LocalTime draftTime = draft.startDateTime().toLocalTime();

            boolean matchesSlot = slots.stream()
                    .anyMatch(slot ->
                            slot.dayOfWeek().equals(draftDay) &&
                                    slot.time().equals(draftTime)
                    );

            assertAll(
                    () -> assertEquals(sessionId, draft.sessionId()),
                    () -> assertEquals(duration, draft.duration()),
                    () -> assertTrue(matchesSlot)
            );
        }
    }
}
