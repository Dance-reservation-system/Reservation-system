package com.reservation.event.domain.exception;

import java.io.Serial;

public class SessionOccurrenceNotStartedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SessionOccurrenceNotStartedException() {
        super("Cannot complete a session occurrence that hasn't started yet.");
    }
}
