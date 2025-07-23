package com.reservation.payment.domain;

record PaymentFailedEvent(PaymentId paymentId,
                                 ReservationId reservationId,
                                 FailureReason reason,
                                 PaymentFailedAt failedAt) implements PaymentEvent {
}
