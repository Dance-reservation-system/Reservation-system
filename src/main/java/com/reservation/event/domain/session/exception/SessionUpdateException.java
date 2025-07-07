package com.reservation.event.domain.session.exception;

import java.io.Serial;

public class SessionUpdateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SessionUpdateException() {
        super("Session cannot be updated when is cancelled");
    }
}
