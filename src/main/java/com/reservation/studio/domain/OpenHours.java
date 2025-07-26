package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.InvalidOpenHoursException;

import java.time.LocalTime;
import java.util.Objects;

record OpenHours(LocalTime from, LocalTime to) implements OpeningSchedule {
    OpenHours {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        if (!from.isBefore(to)) {
            throw new InvalidOpenHoursException(from, to);
        }
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public boolean isOpenAt(LocalTime time) {
        return !time.isBefore(from) && time.isBefore(to);
    }
}
