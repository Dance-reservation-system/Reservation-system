package com.reservation.event.domain.Session;

import com.reservation.event.domain.Session.exception.InvalidRecurrencePatternException;
import com.reservation.event.domain.Session.exception.InvalidTimeRangeException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

record RecurrencePattern(Set<RecurrenceSlot> recurrenceSlots, Duration duration, LocalDate startDate, LocalDate endDate, int intervalWeeks) {
    public RecurrencePattern {
        if(recurrenceSlots == null ||  recurrenceSlots.isEmpty()) {
            throw new InvalidRecurrencePatternException("RecurrencePattern is null or empty");
        }

        if(startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new InvalidTimeRangeException("Start date must be before or equal to end date");
        }

        if(intervalWeeks <= 1){
            throw new InvalidRecurrencePatternException("Interval weeks must be greater than 1");
        }
    }
}
