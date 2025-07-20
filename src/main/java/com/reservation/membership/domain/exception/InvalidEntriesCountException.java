package com.reservation.membership.domain.exception;

import java.io.Serial;

public class InvalidEntriesCountException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Remaining entries must be between 0 and %s";

    public InvalidEntriesCountException(int value) {
        super(String.format(MESSAGE_TEMPLATE, value));
    }
}
