package com.reservation.studio.domain;

import java.time.LocalTime;

sealed interface OpeningSchedule permits ClosedDay, OpenHours {
    boolean isOpen();

    default boolean isOpenAt(LocalTime time) {
        return isOpen();
    }
}
