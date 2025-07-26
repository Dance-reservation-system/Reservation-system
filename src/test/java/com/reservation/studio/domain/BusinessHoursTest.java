package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.InvalidBusinessHoursSizeException;
import com.reservation.studio.domain.exception.MissingBusinessHoursScheduleException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BusinessHoursTest {

    @Test
    void shouldCreateBusinessHoursWithAllDaysAndValidSchedules() {
        BusinessHours businessHours = new BusinessHoursTestBuilder()
                .openAllDays(LocalTime.of(9, 0), LocalTime.of(17, 0))
                .build();

        for (DayOfWeek day : DayOfWeek.values()) {
            assertThat(businessHours.scheduleFor(day)).isInstanceOf(OpeningSchedule.class);
        }
    }

    @Test
    void shouldReportOpenDuringBusinessHours() {
        BusinessHours businessHours = new BusinessHoursTestBuilder()
                .openAllDays(LocalTime.of(9, 0), LocalTime.of(17, 0))
                .build();

        assertThat(businessHours.isOpenOn(DayOfWeek.MONDAY, LocalTime.of(8, 59))).isFalse();
        assertThat(businessHours.isOpenOn(DayOfWeek.MONDAY, LocalTime.of(9, 0))).isTrue();
        assertThat(businessHours.isOpenOn(DayOfWeek.MONDAY, LocalTime.of(10, 0))).isTrue();
        assertThat(businessHours.isOpenOn(DayOfWeek.MONDAY, LocalTime.of(17, 0))).isFalse();
    }

    @Test
    void shouldReturnClosedForClosedDays() {
        BusinessHours businessHours = new BusinessHoursTestBuilder()
                .openDay(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))
                .build();

        assertThat(businessHours.isOpenOn(DayOfWeek.MONDAY, LocalTime.of(10, 0))).isTrue();
        assertThat(businessHours.isOpenOn(DayOfWeek.TUESDAY, LocalTime.of(10, 0))).isFalse();
    }

    @Test
    void shouldThrowIfMapSizeIsNot7() {
        Map<DayOfWeek, OpeningSchedule> invalidMap = new EnumMap<>(DayOfWeek.class);
        invalidMap.put(DayOfWeek.MONDAY, new OpenHours(LocalTime.of(9, 0), LocalTime.of(17, 0)));

        assertThatThrownBy(() -> new BusinessHours(invalidMap)).isInstanceOf(InvalidBusinessHoursSizeException.class);
    }

    @Test
    void shouldThrowIfAnyScheduleIsNull() {
        Map<DayOfWeek, OpeningSchedule> invalidMap = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            invalidMap.put(day, new OpenHours(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        }
        invalidMap.put(DayOfWeek.MONDAY, null);

        assertThatThrownBy(() -> new BusinessHours(invalidMap)).isInstanceOf(MissingBusinessHoursScheduleException.class);
    }

    @Test
    void shouldThrowIfNullHoursMap() {
        assertThatThrownBy(() -> new BusinessHours(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowIfNullTimePassedToIsOpenOn() {
        BusinessHours businessHours = new BusinessHoursTestBuilder()
                .openAllDays(LocalTime.of(9, 0), LocalTime.of(17, 0))
                .build();

        assertThatThrownBy(() -> businessHours.isOpenOn(DayOfWeek.MONDAY, null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowIfNullDayPassedToScheduleFor() {
        BusinessHours businessHours = new BusinessHoursTestBuilder()
                .openAllDays(LocalTime.of(9, 0), LocalTime.of(17, 0))
                .build();

        assertThatThrownBy(() -> businessHours.scheduleFor(null)).isInstanceOf(NullPointerException.class);
    }
}
