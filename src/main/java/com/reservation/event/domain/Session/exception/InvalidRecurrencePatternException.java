package com.reservation.event.domain.Session.exception;

import java.io.Serial;

public class InvalidRecurrencePatternException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidRecurrencePatternException(String message) {
        super(message);
    }
}
