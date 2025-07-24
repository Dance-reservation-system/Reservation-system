package com.reservation.payment.domain;

public sealed interface PaymentEvent permits PaymentCompletedEvent, PaymentFailedEvent {
}