package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class SpecialtyCannotBeNullException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Specialty value cannot be null or empty";

    public SpecialtyCannotBeNullException() {
        super(MESSAGE_TEMPLATE);
    }
}
