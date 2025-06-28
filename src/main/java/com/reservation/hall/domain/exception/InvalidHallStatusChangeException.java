package com.reservation.hall.domain.exception;

public class InvalidHallStatusChangeException extends RuntimeException {
    public InvalidHallStatusChangeException(String message) {
        super(message);
    }
}
