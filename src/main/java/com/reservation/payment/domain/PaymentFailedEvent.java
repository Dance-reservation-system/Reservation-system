package com.reservation.payment.domain;

public record PaymentFailedEvent(PaymentId paymentId,
                                 ReservationId reservationId,
                                 FailureReason reason,
                                 PaymentFailedAt failedAt) implements PaymentEvent {
}
