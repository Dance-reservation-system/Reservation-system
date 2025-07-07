package com.reservation.event.domain.session.exception;

import java.io.Serial;

public class InvalidSessionTitleException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidSessionTitleException(String message) {
        super(message);
    }
}
