package com.reservation.studio.domain.exception;

import java.time.LocalTime;

public class InvalidOpenHoursException extends RuntimeException {

    private static final String MESSAGE = "Opening hours must be before closing time. Opening hour: %s, Closing hour: %s";

    public InvalidOpenHoursException(LocalTime from, LocalTime to) {
        super(String.format(MESSAGE, from, to));
    }
}
