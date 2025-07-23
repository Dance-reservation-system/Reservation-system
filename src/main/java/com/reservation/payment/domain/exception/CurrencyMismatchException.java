package com.reservation.payment.domain.exception;

import java.util.Currency;

public class CurrencyMismatchException extends RuntimeException {

    private static final String MESSAGE = "Currency mismatch: expected %s, but got %s";

    public CurrencyMismatchException(Currency expected, Currency actual) {
        super(String.format(MESSAGE, expected, actual));
    }
}