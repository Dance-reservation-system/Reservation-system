package com.reservation.membership.domain.exception;

public class NoRemainingEntriesException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "No remaining entries to use";

    public NoRemainingEntriesException() {
        super(MESSAGE_TEMPLATE);
    }
}
