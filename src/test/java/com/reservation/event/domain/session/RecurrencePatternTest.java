package com.reservation.event.domain.session;

import com.reservation.event.domain.SessionId;
import com.reservation.event.domain.session.exception.InvalidRecurrencePatternException;
import com.reservation.event.domain.session.exception.InvalidTimeRangeException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecurrencePatternTest {

    private final Set<RecurrenceSlot> validSlots = Set.of(new RecurrenceSlot(DayOfWeek.MONDAY, LocalTime.of(10, 0)));
    private final Duration validDuration = Duration.ofHours(1);
    private final LocalDate validStartDate = LocalDate.of(2025, 1, 1);
    private final LocalDate validEndDate = LocalDate.of(2025, 1, 31);
    private final int validIntervalWeeks = 1;

    @Test
    void shouldThrowWhenRecurrenceSlotIsNull() {
        assertThrows(InvalidRecurrencePatternException.class, () ->
                new RecurrencePattern(null, validDuration, validStartDate, validEndDate, validIntervalWeeks));
    }

    @Test
    void shouldThrowWhenRecurrenceSlotIsEmpty() {
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

        assertAll(
                () -> assertEquals(validSlots, pattern.recurrenceSlots()),
                () -> assertEquals(validDuration, pattern.duration()),
                () -> assertEquals(validStartDate, pattern.startDate()),
                () -> assertEquals(validEndDate, pattern.endDate()),
                () -> assertEquals(validIntervalWeeks, pattern.intervalWeeks())
        );
    }

    @Test
    void shouldGenerateOneOccurrenceWhenOnlyOneSlotMatches() {
        RecurrencePattern pattern = new RecurrencePattern(
                Set.of(new RecurrenceSlot(DayOfWeek.MONDAY, LocalTime.of(10, 0))),
                Duration.ofMinutes(90),
                LocalDate.of(2025, 1, 6),
                LocalDate.of(2025, 1, 6),
                1
        );

        List<SessionOccurrenceDraft> occurrences = pattern.generateOccurrences(SessionId.next());

        assertEquals(1, occurrences.size());
        assertEquals(LocalTime.of(10, 0), occurrences.getFirst().startDateTime().toLocalTime());
        assertEquals(DayOfWeek.MONDAY, occurrences.getFirst().startDateTime().getDayOfWeek());
    }

    @Test
    void shouldGenerateOccurrencesForEachWeek() {
        RecurrencePattern pattern = new RecurrencePattern(
                Set.of(new RecurrenceSlot(DayOfWeek.MONDAY, LocalTime.of(10, 0))),
                Duration.ofHours(1),
                LocalDate.of(2025, 1, 6),
                LocalDate.of(2025, 1, 27),
                1
        );

        List<SessionOccurrenceDraft> occurrences = pattern.generateOccurrences(SessionId.next());

        assertEquals(4, occurrences.size());

        for (int i = 0; i < 4; i++) {
            var expectedDate = LocalDate.of(2025, 1, 6).plusWeeks(i);
            var actual = occurrences.get(i);
            assertEquals(expectedDate, actual.startDateTime().toLocalDate());
            assertEquals(LocalTime.of(10, 0), actual.startDateTime().toLocalTime());
        }
    }

    @Test
    void shouldNotGenerateOccurrencesWhenNoDayMatches() {
        RecurrencePattern pattern = new RecurrencePattern(
                Set.of(new RecurrenceSlot(DayOfWeek.SUNDAY, LocalTime.of(10, 0))),
                Duration.ofHours(1),
                LocalDate.of(2025, 1, 6),
                LocalDate.of(2025, 1, 10),
                1
        );

        List<SessionOccurrenceDraft> occurrences = pattern.generateOccurrences(SessionId.next());

        assertTrue(occurrences.isEmpty());
    }
}
