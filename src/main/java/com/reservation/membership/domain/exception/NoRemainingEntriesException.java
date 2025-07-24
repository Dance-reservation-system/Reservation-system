package com.reservation.membership.domain.exception;

public class NoRemainingEntriesException extends RuntimeException {

    private static final String MESSAGE = "No remaining entries to use";

    public NoRemainingEntriesException() {
        super(MESSAGE);
    }
}
