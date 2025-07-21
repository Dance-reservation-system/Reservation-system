package com.reservation.membership.domain.exception;

import java.io.Serial;

public class InvalidValidityDaysException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidValidityDaysException() {
        super("Validity days must be positive");
    }
}
