package com.reservation.event.domain.session.exception;

import java.io.Serial;

public class NonPositiveDurationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NonPositiveDurationException() {
        super("Duration must be a positive");
    }
}
