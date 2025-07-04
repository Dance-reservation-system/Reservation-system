package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InvalidSpecialityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidSpecialityException(String message) {
        super(message);
    }
}
