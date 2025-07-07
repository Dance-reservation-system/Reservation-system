package com.reservation.event.domain.session;

import com.reservation.event.domain.session.exception.InvalidRecurrencePatternException;
import com.reservation.event.domain.session.exception.InvalidTimeRangeException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecurrencePatternTest {

    private final Set<RecurrenceSlot> validSlots = Set.of(new RecurrenceSlot(DayOfWeek.MONDAY, LocalTime.of(10, 0)));
    private final Duration validDuration = Duration.ofHours(1);
    private final LocalDate validStartDate = LocalDate.of(2025, 1, 1);
    private final LocalDate validEndDate = LocalDate.of(2025, 1, 31);
    private final int validIntervalWeeks = 1;

    @Test
    void shouldThrowWhenRecurrenceSlotsIsNull() {
        assertThrows(InvalidRecurrencePatternException.class, () ->
                new RecurrencePattern(null, validDuration, validStartDate, validEndDate, validIntervalWeeks));
    }

    @Test
    void shouldThrowWhenRecurrenceSlotsIsEmpty() {
        assertThrows(InvalidRecurrencePatternException.class, () ->
                new RecurrencePattern(Set.of(), validDuration, validStartDate, validEndDate, validIntervalWeeks));
    }

    @Test
    void shouldThrowWhenStartDateIsNull() {
        assertThrows(InvalidTimeRangeException.class, () ->
                new RecurrencePattern(validSlots, validDuration, null, validEndDate, validIntervalWeeks));
    }

    @Test
    void shouldThrowWhenEndDateIsNull() {
        assertThrows(InvalidTimeRangeException.class, () ->
                new RecurrencePattern(validSlots, validDuration, validStartDate, null, validIntervalWeeks));
    }

    @Test
    void shouldThrowWhenStartDateIsAfterEndDate() {
        LocalDate startAfterEnd = LocalDate.of(2025, 2, 1);
        LocalDate endBeforeStart = LocalDate.of(2025, 1, 1);

        assertThrows(InvalidTimeRangeException.class, () ->
                new RecurrencePattern(validSlots, validDuration, startAfterEnd, endBeforeStart, validIntervalWeeks));
    }

    @Test
    void shouldThrowWhenIntervalWeeksIsLessThanOne() {
        assertThrows(InvalidRecurrencePatternException.class, () ->
                new RecurrencePattern(validSlots, validDuration, validStartDate, validEndDate, 0));
    }

    @Test
    void shouldCreateRecurrencePatternWithValidParameters() {
        RecurrencePattern pattern = new RecurrencePattern(validSlots, validDuration, validStartDate, validEndDate, validIntervalWeeks);
        assertNotNull(pattern);
    }
}
