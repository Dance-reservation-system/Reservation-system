package com.reservation.membership.domain.exception;

public class InvalidEntriesCountException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Remaining entries must be between 0 and %s";

    public InvalidEntriesCountException(int value) {
        super(String.format(MESSAGE_TEMPLATE, value));
    }
}
