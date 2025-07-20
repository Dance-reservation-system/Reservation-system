package com.reservation.payment.domain.exception;

import java.io.Serial;

public class PaymentAlreadyCompletedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Payment has already been completed";

    public PaymentAlreadyCompletedException() {
        super(MESSAGE);
    }
}