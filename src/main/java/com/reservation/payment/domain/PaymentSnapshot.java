package com.reservation.payment.domain;

record PaymentSnapshot(
            PaymentId paymentId,
            ReservationId reservationId,
            Money amount,
            PaymentMethod method,
            PaymentStatus status,
            PaymentInitiatedAt initiatedAt,
            PaymentCompletedAt completedAt,
            PaymentFailedAt failedAt,
            FailureReason failureReason
    ) {}