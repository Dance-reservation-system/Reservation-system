package com.reservation.payment.domain.exception;

import java.time.LocalDateTime;

public class InvalidPaymentCompletionTimeException extends RuntimeException {

    private static final String MESSAGE = "Payment completion time cannot be in the future: %s";

    public InvalidPaymentCompletionTimeException(LocalDateTime timestamp) {
        super(String.format(MESSAGE, timestamp));
    }
}
