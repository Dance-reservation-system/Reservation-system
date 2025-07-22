package com.reservation.payment.domain.exception;

import java.math.BigDecimal;

public class InvalidMoneyAmountException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Money amount must be non-negative %s";

    public InvalidMoneyAmountException(BigDecimal amount) {
        super(String.format(MESSAGE_TEMPLATE, amount));
    }
}