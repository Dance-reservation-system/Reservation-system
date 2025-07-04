package com.reservation.event.domain.exception;

import java.io.Serial;

public class SessionUpdatedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SessionUpdatedException() {
        super("Session cannot be updated when is cancelled");
    }
}
