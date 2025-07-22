package com.reservation.payment.domain.exception;

import java.util.Currency;

public class CurrencyMismatchException extends RuntimeException {

    public CurrencyMismatchException(Currency expected, Currency actual) {
        super(message(expected, actual));
    }

    public static String message(Currency expected, Currency actual) {
        return "Currency mismatch: expected " + expected + ", but got " + actual;
    }
}