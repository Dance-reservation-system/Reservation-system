package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.InvalidBusinessHoursSizeException;
import com.reservation.studio.domain.exception.MissingBusinessHoursScheduleException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

record BusinessHours(Map<DayOfWeek, OpeningSchedule> hours) {
    BusinessHours {
        Objects.requireNonNull(hours);
        if (hours.size() != 7) {
            throw new InvalidBusinessHoursSizeException(hours.size());
        }

        if (Arrays.stream(DayOfWeek.values()).anyMatch(day -> !hours.containsKey(day) || hours.get(day) == null)) {
            throw new MissingBusinessHoursScheduleException();
        }
    }

    boolean isOpenOn(DayOfWeek day, LocalTime time) {
        return scheduleFor(day).isOpenAt(Objects.requireNonNull(time));
    }

    OpeningSchedule scheduleFor(DayOfWeek day) {
        return hours.get(Objects.requireNonNull(day));
    }
}
