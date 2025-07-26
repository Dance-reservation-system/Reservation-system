package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.InvalidOpenHoursException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

class BusinessHoursTestBuilder {
    private final Map<DayOfWeek, OpeningSchedule> schedule = new EnumMap<>(DayOfWeek.class);

    public BusinessHoursTestBuilder openAllDays(LocalTime from, LocalTime to) {
        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(to, "to cannot be null");
        if (!from.isBefore(to)) {
            throw new InvalidOpenHoursException(from, to);
        }
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new OpenHours(from, to));
        }
        return this;
    }

    public BusinessHoursTestBuilder openDay(DayOfWeek day, LocalTime from, LocalTime to) {
        schedule.put(day, new OpenHours(from, to));
        return this;
    }

    public BusinessHours build() {
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.putIfAbsent(day, ClosedDay.CLOSE);
        }
        return new BusinessHours(schedule);
    }
}
