package com.reservation.payment.domain.exception;

import java.time.LocalDateTime;

public class InvalidPaymentCompletionTimeException extends RuntimeException {

    private static final String MESSAGE = "Payment completion time cannot be in the future: ";

    public InvalidPaymentCompletionTimeException(LocalDateTime timestamp) {
        super(MESSAGE + timestamp);
    }
}
