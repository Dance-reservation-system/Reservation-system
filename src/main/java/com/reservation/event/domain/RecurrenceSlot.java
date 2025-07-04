package com.reservation.event.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

record RecurrenceSlot(DayOfWeek dayOfWeek, LocalTime time) {
    public RecurrenceSlot{
        Objects.requireNonNull(dayOfWeek);
        Objects.requireNonNull(time);
    }
}
