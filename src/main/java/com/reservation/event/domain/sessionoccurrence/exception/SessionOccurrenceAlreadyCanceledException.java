package com.reservation.event.domain.sessionoccurrence.exception;

import java.io.Serial;

public class SessionOccurrenceAlreadyCanceledException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SessionOccurrenceAlreadyCanceledException() {
        super("Session occurrence already canceled.");
    }
}
