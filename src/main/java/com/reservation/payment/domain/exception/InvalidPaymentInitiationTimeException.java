package com.reservation.payment.domain.exception;

import java.time.LocalDateTime;

public class InvalidPaymentInitiationTimeException extends RuntimeException {

    private static final String MESSAGE = "Payment initiation time cannot be in the future: %s";

    public InvalidPaymentInitiationTimeException(LocalDateTime timestamp) {
        super(String.format(MESSAGE, timestamp));
    }
}