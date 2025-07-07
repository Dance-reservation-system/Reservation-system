package com.reservation.event.domain.session.exception;

import java.io.Serial;

public class InvalidSessionCapacityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Session have invalid capacity %s";

    public InvalidSessionCapacityException(int capacity) {
        super(String.format(MESSAGE_TEMPLATE, capacity));
    }
}
