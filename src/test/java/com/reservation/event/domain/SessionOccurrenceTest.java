package com.reservation.event.domain;

import com.reservation.event.domain.exception.SessionOccurrenceAlreadyCanceledException;
import com.reservation.event.domain.exception.SessionOccurrenceNotStartedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

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
        SessionOccurrence newSessionOccurrence = SessionOccurrence.create(newSessionOccurrenceId,
                newSessionId, occurrenceStartDate, occurrenceDuration);

        // Then
        assertEquals(newSessionOccurrenceId, newSessionOccurrence.getId());
        assertEquals(newSessionId, newSessionOccurrence.getSessionId());
        assertEquals(occurrenceStartDate, newSessionOccurrence.getStartDateTime());
        assertEquals(occurrenceDuration, newSessionOccurrence.getDuration());
        assertEquals(SessionOccurrenceStatus.SCHEDULED, newSessionOccurrence.getStatus());
    }

    @Test
    void shouldCancelScheduledSessionOccurrence() {
        //Given & When
        sessionOccurrence.cancel();

        //Then
        assertEquals(SessionOccurrenceStatus.CANCELLED, sessionOccurrence.getStatus());
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
        assertEquals(SessionOccurrenceStatus.COMPLETED, sessionOccurrence.getStatus());
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
    void shouldReturnTrueIfSessionOccurrenceIsScheduled() {
        //Given & When
        boolean isActive = sessionOccurrence.isActive();

        //Then
        assertTrue(isActive);
    }

    @Test
    void shouldReturnFalseIfSessionOccurrenceIsNotScheduled() {
        //Given
        sessionOccurrence.cancel();

        //When
        boolean isActive = sessionOccurrence.isActive();

        //Then
        assertFalse(isActive);
    }
}
