package com.reservation.payment.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.payment.domain.exception.PaymentAlreadyCompletedException;
import com.reservation.payment.domain.exception.PaymentAlreadyFailedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Payment implements AggregateRoot<PaymentEvent> {

    private final PaymentId paymentId;
    private final ReservationId reservationId;
    private final Money amount;
    private final PaymentMethod method;
    private PaymentStatus status;
    private final PaymentInitiatedAt initiatedAt;
    private PaymentCompletedAt completedAt;
    private PaymentFailedAt failedAt;
    private FailureReason failureReason;

    private final List<PaymentEvent> events = new ArrayList<>();

    private Payment(PaymentId paymentId, ReservationId reservationId, Money amount,
                    PaymentMethod method, PaymentInitiatedAt initiatedAt) {
        this.paymentId = Objects.requireNonNull(paymentId);
        this.reservationId = Objects.requireNonNull(reservationId);
        this.amount = Objects.requireNonNull(amount);
        this.method = Objects.requireNonNull(method);
        this.initiatedAt = Objects.requireNonNull(initiatedAt);
        this.status = PaymentStatus.INITIATED;
    }

    public static Payment create(PaymentId id, ReservationId reservationId, Money amount,
                                   PaymentMethod method, PaymentInitiatedAt initiatedAt) {
        return new Payment(id, reservationId, amount, method, initiatedAt);
    }

    public PaymentId getPaymentId() {
        return paymentId;
    }

    public ReservationId getReservationId() {
        return reservationId;
    }

    public boolean hasSameReservation(ReservationId id) {
        return this.reservationId.equals(id);
    }

    public void markAsCompleted(PaymentCompletedAt completedAt) {
        requireNotCompletedOrFailed();
        this.completedAt = Objects.requireNonNull(completedAt);
        this.status = PaymentStatus.COMPLETED;
        registerEvent(new PaymentCompletedEvent(paymentId, reservationId, completedAt));
    }

    public void markAsFailed(PaymentFailedAt failedAt, FailureReason reason) {
        requireNotCompletedOrFailed();
        this.failedAt = Objects.requireNonNull(failedAt);
        this.failureReason = Objects.requireNonNull(reason);
        this.status = PaymentStatus.FAILED;
        registerEvent(new PaymentFailedEvent(paymentId, reservationId, reason, failedAt));
    }

    private void requireNotCompletedOrFailed() {
        if (isCompleted()) {
            throw new PaymentAlreadyCompletedException();
        }
        if (isFailed()) {
            throw new PaymentAlreadyFailedException();
        }
    }

    private boolean isCompleted() {
        return status == PaymentStatus.COMPLETED;
    }

    private boolean isFailed() {
        return status == PaymentStatus.FAILED;
    }

    private boolean isInitiated() {
        return status == PaymentStatus.INITIATED;
    }

    public PaymentSnapshot describeSnapshot() {
        return new PaymentSnapshot(
                paymentId,
                reservationId,
                amount,
                method,
                status,
                initiatedAt,
                completedAt,
                failedAt,
                failureReason
        );
    }

    @Override
    public void registerEvent(PaymentEvent event) {
       events.add(event);
    }

    @Override
    public List<PaymentEvent> pullEvents() {
        List<PaymentEvent> copy = List.copyOf(events);
        this.events.clear();
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment other)) return false;
        return Objects.equals(paymentId, other.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }
}
