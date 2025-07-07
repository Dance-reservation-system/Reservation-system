package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;
import com.reservation.event.domain.session.exception.InvalidRecurrencePatternException;
import com.reservation.event.domain.session.exception.InvalidTimeRangeException;

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
            throw new InvalidRecurrencePatternException("Interval weeks must be greater than 0");
        }
    }

    public List<SessionOccurrenceDraft> generateOccurrences(SessionId sessionId) {
        List<SessionOccurrenceDraft> sessionOccurrences = new ArrayList<>();
        LocalDate weekStart = startDate;

        while (!weekStart.isAfter(endDate)) {
            for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
                LocalDate currentDate = weekStart.plusDays(dayOffset);
                if (currentDate.isAfter(endDate)) break;
                for (RecurrenceSlot recurrenceSlot : recurrenceSlots) {
                    if (recurrenceSlot.dayOfWeek().equals(currentDate.getDayOfWeek())) {
                        LocalDateTime startDateTime = currentDate.atTime(recurrenceSlot.time());
                        sessionOccurrences.add(new SessionOccurrenceDraft(sessionId, startDateTime, duration));
                    }
                }
            }
            weekStart = weekStart.plusWeeks(intervalWeeks);
        }

        return sessionOccurrences;
    }
}
