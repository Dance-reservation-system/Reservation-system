package com.reservation.payment.domain;

enum PaymentStatus {
    INITIATED,
    COMPLETED,
    FAILED;

    public boolean isCompleted() {
        return this == COMPLETED;
    }
}