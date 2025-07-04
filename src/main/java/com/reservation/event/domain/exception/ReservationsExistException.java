package com.reservation.event.domain.exception;

import java.io.Serial;

public class ReservationsExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ReservationsExistException() {
        super("Session cannot be cancelled when reservations already exist");
    }
}
