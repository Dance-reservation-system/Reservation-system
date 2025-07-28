package com.reservation.studio.domain.exception;

public class InvalidBusinessHoursSizeException extends RuntimeException {

    private static final String MESSAGE = "Business hours must contain exactly 7 entries, but got %d";

    public InvalidBusinessHoursSizeException(int size) {
        super(String.format(MESSAGE, size));
    }
}
