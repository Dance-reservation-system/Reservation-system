package com.reservation.payment.domain.exception;

public class InvalidFailureReasonException extends RuntimeException {

    private static final String MESSAGE = "Invalid failure reason (must not be blank): '%s'";

    public InvalidFailureReasonException(String reason) {
        super(String.format(MESSAGE, reason));
    }
}