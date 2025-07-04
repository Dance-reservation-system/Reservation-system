package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InvalidBioException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidBioException(String message) {
        super(message);
    }
}
