package com.reservation.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class PaymentTestBuilder {
    private PaymentId paymentId = PaymentId.next();
    private ReservationId reservationId = ReservationId.next();
    private Money amount = new Money(BigDecimal.valueOf(50));
    private PaymentMethod method = PaymentMethod.CARD;
    private PaymentInitiatedAt initiatedAt = new PaymentInitiatedAt(LocalDateTime.now().plusDays(1));

    public PaymentTestBuilder withAmount(BigDecimal value) {
        this.amount = new Money(value);
        return this;
    }

    public PaymentTestBuilder withInitiatedAt(PaymentInitiatedAt initiatedAt) {
        this.initiatedAt = initiatedAt;
        return this;
    }

    public Payment build() {
        return Payment.create(paymentId, reservationId, amount, method, initiatedAt);
    }
}
