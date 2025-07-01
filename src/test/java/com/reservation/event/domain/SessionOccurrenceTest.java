package com.reservation.event.domain;

import com.reservation.event.domain.exception.SessionOccurrenceAlreadyCanceledException;
import com.reservation.event.domain.exception.SessionOccurrenceNotStartedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SessionOccurrenceTest {

    SessionOccurrence sessionOccurrence;
    SessionId sessionId;
    SessionOccurrenceId sessionOccurrenceId;
    LocalDateTime occurrenceStartDate;
    Duration occurrenceDuration;

    @BeforeEach
    void setUp() {
        sessionId = SessionId.next();
        sessionOccurrenceId = SessionOccurrenceId.next();
        occurrenceStartDate = LocalDateTime.now().minusDays(5);
        occurrenceDuration = Duration.ofHours(2);
        sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate, occurrenceDuration);
    }

    @Test
    void shouldCreateValidOccurrence() {
        // Given & When
        SessionOccurrenceId newSessionOccurrenceId = SessionOccurrenceId.next();
        SessionId newSessionId = SessionId.next();
        Duration newOccurrenceDuration = Duration.ofHours(2);
        SessionOccurrence newSessionOccurrence = SessionOccurrence.create(newSessionOccurrenceId,
                newSessionId, occurrenceStartDate, newOccurrenceDuration);

        // Then
        assertAll(
                () -> assertEquals(newSessionOccurrenceId, newSessionOccurrence.getId()),
                () -> assertEquals(newSessionId, newSessionOccurrence.getSessionId()),
                () -> assertTrue(newSessionOccurrence.isScheduled()),
                () -> assertTrue(newSessionOccurrence.isLongerThan(Duration.ofHours(1))),
                () -> assertFalse(newSessionOccurrence.isCanceled()),
                () -> assertFalse(newSessionOccurrence.isCompleted())
        );
    }

    @Test
    void shouldCancelScheduledSessionOccurrence() {
        //Given & When
        sessionOccurrence.cancel();

        //Then
        assertTrue(sessionOccurrence.isCanceled());
    }

    @Test
    void shouldThrowExceptionWhenTryToCancelAlreadyCancelled() {
        //Given
        sessionOccurrence.cancel();

        //When & Then
        assertThrows(SessionOccurrenceAlreadyCanceledException.class,
                sessionOccurrence::cancel);
    }

    @Test
    void shouldChangeSessionOccurrenceStatusToCompleted() {
        //Given & When
        sessionOccurrence.complete();

        //Then
        assertFalse(sessionOccurrence.isScheduled());
        assertTrue(sessionOccurrence.isCompleted());
    }

    @Test
    void shouldThrowExceptionWhenTryToCompleteAlreadyCancelledSession() {
        //Given & When
        sessionOccurrence.cancel();

        //Then
        assertThrows(SessionOccurrenceAlreadyCanceledException.class,
                ()->sessionOccurrence.complete());
    }

    @Test
    void shouldThrowExceptionIfSessionOccurrenceIsNotStartedAndTryToComplete() {
        //Given & When
        SessionOccurrence sessionOccurrenceNotStarted = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate.plusDays(7), occurrenceDuration);

        //Then
        assertThrows(SessionOccurrenceNotStartedException.class,
                sessionOccurrenceNotStarted::complete);
    }

    @Test
    void shouldReturnTrueIfSessionOccurrenceIsStarted() {
        //Given & When
        boolean hasStarted = sessionOccurrence.hasStarted();

        //Then
        assertTrue(hasStarted);

    }

    @Test
    void shouldReturnTrueIfSessionOccurrenceIsScheduled() {
        //Given & When
        boolean isScheduled = sessionOccurrence.isScheduled();

        //Then
        assertTrue(isScheduled);
    }

    @Test
    void shouldReturnFalseIfSessionOccurrenceIsNotScheduled() {
        //Given
        sessionOccurrence.cancel();

        //When
        boolean isActive = sessionOccurrence.isScheduled();

        //Then
        assertFalse(isActive);
    }

    @Test
    void shouldAddCreateEventToSessionOccurrence() {

        //Given &  When
        List<SessionOccurrenceEvent> sessionOccurrenceEvents = sessionOccurrence.pullEvents();
        SessionOccurrenceEvent sessionOccurrenceEvent = sessionOccurrenceEvents.get(0);

        // Then
        assertAll(
                () -> assertEquals(1, sessionOccurrenceEvents.size()),
                () -> {
                    SessionOccurrenceCreated createdEvent = (SessionOccurrenceCreated) sessionOccurrenceEvent;
                    assertEquals(sessionOccurrence.getId(), createdEvent.id());
                }
        );
    }

    @Test
    void shouldCreateCancelEventToSessionOccurrence() {
        //Given
        sessionOccurrence.cancel();
        //When
        List<SessionOccurrenceEvent> sessionOccurrenceEvents = sessionOccurrence.pullEvents();
        SessionOccurrenceEvent sessionOccurrenceEvent = sessionOccurrenceEvents.get(1);

        // Then
        assertAll(
                () -> assertEquals(2, sessionOccurrenceEvents.size()),
                () -> {
                    SessionOccurrenceCancelled createdEvent = (SessionOccurrenceCancelled) sessionOccurrenceEvent;
                    assertEquals(sessionOccurrence.getId(), createdEvent.id());
                }
        );
    }

    @Test
    void shouldCreateCompletedEventToSessionOccurrence() {
        //Given
        sessionOccurrence.complete();
        //When
        List<SessionOccurrenceEvent> sessionOccurrenceEvents = sessionOccurrence.pullEvents();
        SessionOccurrenceEvent sessionOccurrenceEvent = sessionOccurrenceEvents.get(1);

        // Then
        assertAll(
                () -> assertEquals(2, sessionOccurrenceEvents.size()),
                () -> {
                    SessionOccurrenceCompleted createdEvent = (SessionOccurrenceCompleted) sessionOccurrenceEvent;
                    assertEquals(sessionOccurrence.getId(), createdEvent.id());
                }
        );
    }

}
