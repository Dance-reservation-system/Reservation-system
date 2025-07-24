package com.reservation.payment.domain.exception;

import java.time.LocalDateTime;

public class InvalidPaymentFailureTimeException extends RuntimeException {

    private static final String MESSAGE = "Payment initiation time cannot be in the future: %s";

    public InvalidPaymentFailureTimeException(LocalDateTime timestamp) {
        super(String.format(MESSAGE, timestamp));
    }
}
