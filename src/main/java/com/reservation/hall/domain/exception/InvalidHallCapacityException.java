package com.reservation.hall.domain.exception;

import java.io.Serial;

public class InvalidHallCapacityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Hall have invalid capacity %s";

    public InvalidHallCapacityException(int capacity) {
        super(String.format(MESSAGE_TEMPLATE, capacity));
    }
}
