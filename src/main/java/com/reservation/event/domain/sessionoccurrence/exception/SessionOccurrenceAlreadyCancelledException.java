package com.reservation.event.domain.sessionoccurrence.exception;

import java.io.Serial;

public class SessionOccurrenceAlreadyCancelledException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SessionOccurrenceAlreadyCancelledException() {
        super("Session occurrence already cancelled.");
    }
}
