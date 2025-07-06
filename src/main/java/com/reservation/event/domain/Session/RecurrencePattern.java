package com.reservation.event.domain.Session;

import com.reservation.event.domain.Session.exception.InvalidRecurrencePatternException;
import com.reservation.event.domain.Session.exception.InvalidTimeRangeException;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

record RecurrencePattern(Set<RecurrenceSlot> recurrenceSlots, Duration duration, LocalDate startDate, LocalDate endDate,
                         int intervalWeeks) {
    public RecurrencePattern {
        if (recurrenceSlots == null || recurrenceSlots.isEmpty()) {
            throw new InvalidRecurrencePatternException("RecurrencePattern is null or empty");
        }

        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new InvalidTimeRangeException("Start date must be before or equal to end date");
        }

        if (intervalWeeks < 1) {
            throw new InvalidRecurrencePatternException("Interval weeks must be greater than 1");
        }
    }

    public List<SessionOccurrenceDraft> generateOccurrences(SessionId sessionId) {
        List<SessionOccurrenceDraft> sessionOccurrences = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            for (RecurrenceSlot recurrenceSlot : recurrenceSlots) {
                if (recurrenceSlot.dayOfWeek().equals(currentDate.getDayOfWeek())) {
                    LocalDateTime startDateTime = currentDate.atTime(recurrenceSlot.time());
                    sessionOccurrences.add(new SessionOccurrenceDraft(sessionId, startDateTime, duration));
                }
            }

            currentDate = currentDate.plusDays(1);

            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                currentDate = currentDate.plusWeeks(intervalWeeks - 1);
            }
        }

        return sessionOccurrences;
    }
}
