package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;
import com.reservation.event.domain.session.exception.InvalidRecurrencePatternException;
import com.reservation.event.domain.session.exception.InvalidTimeRangeException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public record RecurrencePattern(
        Set<RecurrenceSlot> recurrenceSlots,
        Duration duration,
        LocalDate startDate,
        LocalDate endDate,
        int intervalWeeks
) {

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
        return Stream.iterate(startDate, date -> !date.isAfter(endDate), date -> date.plusWeeks(intervalWeeks))
                .flatMap(weekStart -> generateWeekOccurrences(sessionId, weekStart))
                .collect(toList());
    }

    private Stream<SessionOccurrenceDraft> generateWeekOccurrences(SessionId sessionId, LocalDate weekStart) {
        return IntStream.range(0, 7)
                .mapToObj(weekStart::plusDays)
                .filter(this::isWithinRange)
                .flatMap(date -> recurrenceSlots.stream()
                        .filter(slot -> slot.dayOfWeek().equals(date.getDayOfWeek()))
                        .map(slot -> new SessionOccurrenceDraft(sessionId, date.atTime(slot.time()), duration)));
    }

    private boolean isWithinRange(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
