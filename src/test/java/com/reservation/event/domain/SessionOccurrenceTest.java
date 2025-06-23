package com.reservation.event.domain;

import com.reservation.event.domain.exception.SessionOccurrenceNotStartedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

    }

    @Test
    void shouldCreateValidOccurrence() {
        // Given & When
        SessionOccurrence sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate, occurrenceDuration);

        // Then
        assertEquals(sessionOccurrenceId, sessionOccurrence.getId());
        assertEquals(sessionId, sessionOccurrence.getSessionId());
        assertEquals(occurrenceStartDate, sessionOccurrence.getStartDateTime());
        assertEquals(occurrenceDuration, sessionOccurrence.getDuration());
        assertEquals(SessionOccurrenceStatus.SCHEDULED, sessionOccurrence.getStatus());
    }

    @Test
    void shouldCancelScheduledSessionOccurrence() {
        //Given
        SessionOccurrence sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate, occurrenceDuration);

        //When
        sessionOccurrence.cancel();

        //Then
        assertEquals(SessionOccurrenceStatus.CANCELLED, sessionOccurrence.getStatus());
    }

    @Test
    void shouldChangeSessionOccurrenceStatusToCompleted() {
        //Given
        SessionOccurrence sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate, occurrenceDuration);

        //When
        sessionOccurrence.complete();

        //Then
        assertEquals(SessionOccurrenceStatus.COMPLETED, sessionOccurrence.getStatus());
    }

    @Test
    void shouldThrowExceptionIfSessionOccurrenceIsNotStartedAndTryToComplete() {
        //Given & When
        SessionOccurrence sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate.plusDays(7), occurrenceDuration);

        //Then
        assertThrows(SessionOccurrenceNotStartedException.class,
                sessionOccurrence::complete);
    }

    @Test
    void shouldReturnTrueIfSessionOccurrenceIsScheduled() {
        //Given
        SessionOccurrence sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate, occurrenceDuration);

        //When
        sessionOccurrence.isActive();

        //Then
        assertTrue(sessionOccurrence.isActive());
    }

    @Test
    void shouldReturnFalseIfSessionOccurrenceIsNotScheduled() {
        //Given
        SessionOccurrence sessionOccurrence = SessionOccurrence.create(sessionOccurrenceId,
                sessionId, occurrenceStartDate, occurrenceDuration);
        sessionOccurrence.cancel();

        //When
        sessionOccurrence.isActive();

        //Then
        assertFalse(sessionOccurrence.isActive());
    }
}
