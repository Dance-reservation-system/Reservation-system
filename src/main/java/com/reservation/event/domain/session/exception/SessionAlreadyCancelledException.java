package com.reservation.event.domain.session.exception;

import com.reservation.event.domain.SessionId;

import java.io.Serial;

public class SessionAlreadyCancelledException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Session with %s is already cancelled";

    public SessionAlreadyCancelledException(SessionId sessionId) {
        super(String.format(MESSAGE_TEMPLATE, sessionId));
    }
}
