package com.reservation.payment.domain;

public record PaymentCompletedEvent(PaymentId paymentId,
                                    ReservationId reservationId,
                                    PaymentCompletedAt completedAt) implements PaymentEvent {
}
