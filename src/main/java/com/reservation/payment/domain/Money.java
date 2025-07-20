package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.InvalidMoneyAmountException;

import java.math.BigDecimal;
import java.util.Objects;

record Money(BigDecimal value) {
    public Money {
        Objects.requireNonNull(value);
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyAmountException(value);
        }
    }
}