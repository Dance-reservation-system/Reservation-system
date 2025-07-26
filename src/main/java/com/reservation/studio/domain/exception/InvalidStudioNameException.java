package com.reservation.studio.domain.exception;

public class InvalidStudioNameException extends RuntimeException {

    private static final String MESSAGE = "Studio name cannot be %s";

    public InvalidStudioNameException(String message) {
        super(String.format(MESSAGE, message));
    }
}
