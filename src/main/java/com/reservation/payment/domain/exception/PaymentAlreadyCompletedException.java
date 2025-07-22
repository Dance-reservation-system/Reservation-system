package com.reservation.payment.domain.exception;

public class PaymentAlreadyCompletedException extends RuntimeException {

    private static final String MESSAGE = "Payment has already been completed";

    public PaymentAlreadyCompletedException() {
        super(MESSAGE);
    }
}