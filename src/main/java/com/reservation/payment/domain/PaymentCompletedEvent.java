package com.reservation.payment.domain;

record PaymentCompletedEvent(PaymentId paymentId,
                                    ReservationId reservationId,
                                    PaymentCompletedAt completedAt) implements PaymentEvent {
}
