package com.reservation.payment.domain;

import com.reservation.payment.domain.exception.PaymentAlreadyCompletedException;
import com.reservation.payment.domain.exception.PaymentAlreadyFailedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentTest {

    @Test
    void shouldCreatePaymentCorrectly() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentSnapshot snapshot = payment.describeSnapshot();

        assertAll(
                () -> assertEquals(PaymentStatus.INITIATED, snapshot.status()),
                () -> assertEquals(BigDecimal.valueOf(50), snapshot.amount().value()),
                () -> assertNotNull(snapshot.initiatedAt())
        );
    }

    @Test
    void shouldMarkPaymentAsCompleted() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentCompletedAt completedAt = new PaymentCompletedAt(LocalDateTime.now());

        payment.markAsCompleted(completedAt);
        PaymentSnapshot snapshot = payment.describeSnapshot();

        assertAll(
                () -> assertEquals(PaymentStatus.COMPLETED, snapshot.status()),
                () -> assertEquals(completedAt, snapshot.completedAt())
        );
    }

    @Test
    void shouldMarkPaymentAsFailed() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentFailedAt failedAt = new PaymentFailedAt(LocalDateTime.now());
        FailureReason reason = new FailureReason("Insufficient funds");

        payment.markAsFailed(failedAt, reason);
        PaymentSnapshot snapshot = payment.describeSnapshot();

        assertAll(
                () -> assertEquals(PaymentStatus.FAILED, snapshot.status()),
                () -> assertEquals(failedAt, snapshot.failedAt()),
                () -> assertEquals(reason, snapshot.failureReason())
        );
    }

    @Test
    void shouldNotAllowCompletingAnAlreadyCompletedPayment() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentCompletedAt completedAt1 = new PaymentCompletedAt(LocalDateTime.now());
        PaymentCompletedAt completedAt2 = new PaymentCompletedAt(LocalDateTime.now().plusSeconds(1));

        payment.markAsCompleted(completedAt1);

        assertThrows(PaymentAlreadyCompletedException.class,
                () -> payment.markAsCompleted(completedAt2));
    }

    @Test
    void shouldNotAllowFailingAnAlreadyFailedPayment() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentFailedAt failedAt1 = new PaymentFailedAt(LocalDateTime.now());
        FailureReason reason1 = new FailureReason("fail");

        PaymentFailedAt failedAt2 = new PaymentFailedAt(LocalDateTime.now().plusSeconds(1));
        FailureReason reason2 = new FailureReason("again");

        payment.markAsFailed(failedAt1, reason1);

        assertThrows(PaymentAlreadyFailedException.class,
                () -> payment.markAsFailed(failedAt2, reason2));
    }

    @Test
    void shouldNotAllowFailingAfterCompletion() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentCompletedAt completedAt = new PaymentCompletedAt(LocalDateTime.now());
        PaymentFailedAt failedAt = new PaymentFailedAt(LocalDateTime.now().plusSeconds(1));
        FailureReason reason = new FailureReason("too late");

        payment.markAsCompleted(completedAt);

        assertThrows(PaymentAlreadyCompletedException.class,
                () -> payment.markAsFailed(failedAt, reason));
    }

    @Test
    void shouldNotAllowCompletingAfterFailure() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentFailedAt failedAt = new PaymentFailedAt(LocalDateTime.now());
        FailureReason reason = new FailureReason("too early");

        PaymentCompletedAt completedAt = new PaymentCompletedAt(LocalDateTime.now().plusSeconds(1));

        payment.markAsFailed(failedAt, reason);

        assertThrows(PaymentAlreadyFailedException.class,
                () -> payment.markAsCompleted(completedAt));
    }


    @Test
    void shouldRegisterCompletedEvent() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentCompletedAt completedAt = new PaymentCompletedAt(LocalDateTime.now());

        payment.markAsCompleted(completedAt);

        var events = payment.pullEvents();
        assertEquals(1, events.size());
        assertInstanceOf(PaymentCompletedEvent.class, events.getFirst());

        PaymentCompletedEvent event = (PaymentCompletedEvent) events.getFirst();
        assertEquals(payment.getPaymentId(), event.paymentId());
        assertEquals(payment.getReservationId(), event.reservationId());
        assertEquals(completedAt, event.completedAt());
    }

    @Test
    void shouldRegisterFailedEvent() {
        Payment payment = new PaymentTestBuilder().build();
        PaymentFailedAt failedAt = new PaymentFailedAt(LocalDateTime.now());
        FailureReason reason = new FailureReason("Too late");

        payment.markAsFailed(failedAt, reason);

        var events = payment.pullEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof PaymentFailedEvent);

        PaymentFailedEvent event = (PaymentFailedEvent) events.get(0);
        assertEquals(reason, event.reason());
        assertEquals(failedAt, event.failedAt());
    }

    @Test
    void shouldReturnEmptyEventListIfNoEventsRegistered() {
        Payment payment = new PaymentTestBuilder().build();
        var events = payment.pullEvents();

        assertTrue(events.isEmpty());
    }

    @Test
    void shouldThrowOnBlankFailureReason() {
        assertThrows(IllegalArgumentException.class, () -> new FailureReason("   "));
    }

    @Test
    void shouldThrowOnNullCompletedAt() {
        assertThrows(NullPointerException.class, () -> new PaymentCompletedAt(null));
    }

    @Test
    void shouldThrowOnNullFailedAt() {
        assertThrows(NullPointerException.class, () -> new PaymentFailedAt(null));
    }
}
