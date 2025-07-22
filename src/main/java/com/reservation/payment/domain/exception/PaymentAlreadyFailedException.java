package com.reservation.payment.domain.exception;

public class PaymentAlreadyFailedException extends RuntimeException {

    private static final String MESSAGE = "Payment has already failed";

    public PaymentAlreadyFailedException() {
        super(MESSAGE);
    }
}