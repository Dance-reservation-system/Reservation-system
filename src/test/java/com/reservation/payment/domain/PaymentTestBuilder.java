package com.reservation.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class PaymentTestBuilder {

    private PaymentId paymentId = PaymentId.next();
    private ReservationId reservationId = ReservationId.next();
    private Currency currency = Currency.getInstance("PLN");
    private Money money = new Money(BigDecimal.valueOf(50), currency);
    private PaymentInitiatedAt initiatedAt = new PaymentInitiatedAt(LocalDateTime.now().minusSeconds(1));

    public Payment build() {
        return Payment.create(paymentId, reservationId, money, PaymentMethod.CARD, initiatedAt);
    }

    public PaymentTestBuilder withInitiatedAt(LocalDateTime time) {
        this.initiatedAt = new PaymentInitiatedAt(time);
        return this;
    }

    public PaymentTestBuilder withAmount(BigDecimal value) {
        this.money = new Money(value, this.currency);
        return this;
    }

    public PaymentTestBuilder withAmount(BigDecimal value, Currency currency) {
        this.currency = currency;
        this.money = new Money(value, currency);
        return this;
    }

    public PaymentTestBuilder withPaymentId(PaymentId id) {
        this.paymentId = id;
        return this;
    }

    public PaymentTestBuilder withReservationId(ReservationId id) {
        this.reservationId = id;
        return this;
    }

    public PaymentTestBuilder withCurrency(Currency currency) {
        this.currency = currency;
        this.money = new Money(this.money.amount(), currency);
        return this;
    }
}
