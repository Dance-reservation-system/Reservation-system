package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class BioCannotBeNullException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Bio value cannot be null";

    public BioCannotBeNullException() {
        super(MESSAGE_TEMPLATE);
    }
}
