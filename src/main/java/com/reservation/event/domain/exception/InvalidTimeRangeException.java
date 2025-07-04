package com.reservation.event.domain.exception;

import java.io.Serial;

public class InvalidTimeRangeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTimeRangeException(String message) {
        super(message);
    }
}
