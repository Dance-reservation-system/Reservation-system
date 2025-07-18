package com.reservation.reservation.domain.exception;

import java.io.Serial;

public class ReservationAlreadyCancelledException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ReservationAlreadyCancelledException() {
        super("Reservation has already been cancelled");
    }
}
