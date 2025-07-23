package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.CurrencyMismatchException;
import com.reservation.payment.domain.exception.InvalidMoneyAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {

    private static final Currency PLN = Currency.getInstance("PLN");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    void shouldCreateValidMoney() {
        Money money = new Money(new BigDecimal("10.00"), PLN);
        assertEquals(new BigDecimal("10.00"), money.amount());
        assertEquals(PLN, money.currency());
    }

    @Test
    void shouldThrowWhenAmountIsNegative() {
        BigDecimal negativeAmount = new BigDecimal("-1");
        assertThrows(InvalidMoneyAmountException.class, () -> new Money(negativeAmount, PLN));
    }

    @Test
    void shouldThrowWhenCurrencyIsNull() {
        BigDecimal amount = new BigDecimal("10");
        assertThrows(NullPointerException.class, () -> new Money(amount, null));
    }

    @Test
    void shouldThrowWhenAmountIsNull() {
        assertThrows(NullPointerException.class, () -> new Money(null, PLN));
    }

    @Test
    void shouldAddMoneyWithSameCurrency() {
        Money m1 = new Money(new BigDecimal("5"), PLN);
        Money m2 = new Money(new BigDecimal("10"), PLN);

        Money result = m1.add(m2);

        assertEquals(new BigDecimal("15"), result.amount());
        assertEquals(PLN, result.currency());
    }

    @Test
    void shouldThrowWhenAddingDifferentCurrency() {
        Money m1 = new Money(new BigDecimal("5"), PLN);
        Money m2 = new Money(new BigDecimal("10"), EUR);

        assertThrows(CurrencyMismatchException.class, () -> m1.add(m2));
    }

    @Test
    void shouldSubtractProperly() {
        Money m1 = new Money(new BigDecimal("10"), PLN);
        Money m2 = new Money(new BigDecimal("4"), PLN);

        Money result = m1.subtract(m2);
        assertEquals(new BigDecimal("6"), result.amount());
    }

    @Test
    void shouldThrowWhenSubtractionResultNegative() {
        Money m1 = new Money(new BigDecimal("5"), PLN);
        Money m2 = new Money(new BigDecimal("10"), PLN);

        assertThrows(InvalidMoneyAmountException.class, () -> m1.subtract(m2));
    }

    @Test
    void shouldReturnTrueWhenGreaterThan() {
        Money m1 = new Money(new BigDecimal("10"), PLN);
        Money m2 = new Money(new BigDecimal("5"), PLN);

        assertTrue(m1.isGreaterThan(m2));
    }

    @Test
    void shouldReturnFalseWhenNotGreaterThan() {
        Money m1 = new Money(new BigDecimal("5"), PLN);
        Money m2 = new Money(new BigDecimal("10"), PLN);

        assertFalse(m1.isGreaterThan(m2));
    }

    @Test
    void shouldReturnTrueIfZero() {
        Money m = new Money(BigDecimal.ZERO, PLN);
        assertTrue(m.isZero());
    }

    @Test
    void shouldReturnFalseIfNotZero() {
        Money m = new Money(new BigDecimal("0.01"), PLN);
        assertFalse(m.isZero());
    }
}
