package com.reservation.studio.domain.exception;

public class StudioAlreadyActiveException extends RuntimeException {

    private static final String MESSAGE = "Studio is already active.";

    public StudioAlreadyActiveException() {
        super(MESSAGE);
    }
}
