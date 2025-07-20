package com.reservation.payment.domain;

sealed interface PaymentEvent permits PaymentCompletedEvent, PaymentFailedEvent {
}