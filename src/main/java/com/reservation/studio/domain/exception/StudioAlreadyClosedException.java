package com.reservation.studio.domain.exception;

public class StudioAlreadyClosedException extends RuntimeException {

    private static final String MESSAGE = "Studio is already closed.";

    public StudioAlreadyClosedException() {
        super(MESSAGE);
    }
}
