package com.reservation.studio.domain.exception;

public class InvalidStudioNameException extends RuntimeException {

    private static final String MESSAGE = "Studio name cannot be %s";

    public InvalidStudioNameException(String invalidName) {
        super(String.format(MESSAGE, invalidName));
    }
}
