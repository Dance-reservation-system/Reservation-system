package com.reservation.event.domain.exception;

import java.io.Serial;

public class SessionAlreadyCancelledException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SessionAlreadyCancelledException() {
        super("Session is already cancelled");
    }
}
