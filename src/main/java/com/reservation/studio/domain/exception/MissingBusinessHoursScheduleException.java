package com.reservation.studio.domain.exception;

public class MissingBusinessHoursScheduleException extends RuntimeException {

    private static final String MESSAGE = "Missing or invalid business hours schedule";

    public MissingBusinessHoursScheduleException() {
        super(String.format(MESSAGE));
    }
}
