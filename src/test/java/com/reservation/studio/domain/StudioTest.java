package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.ContactDetailsCannotBeBlankException;
import com.reservation.studio.domain.exception.InvalidCancellationThresholdException;
import com.reservation.studio.domain.exception.InvalidStudioNameException;
import com.reservation.studio.domain.exception.StudioAlreadyActiveException;
import com.reservation.studio.domain.exception.StudioAlreadyClosedException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudioTest {
    @Test
    void shouldCreateStudioAndEmitCreatedEvent() {
        Studio studio = new StudioTestBuilder().build();

        assertAll(
                () -> assertFalse(studio.isActive()),
                () -> assertTrue(studio.pullEvents().stream().anyMatch(StudioCreatedEvent.class::isInstance))
        );
    }

    @Test
    void shouldRenameStudioAndEmitRenamedEvent() {
        Studio studio = new StudioTestBuilder().build();
        StudioName newName = new StudioName("New Studio Name");

        studio.rename(newName);

        assertAll(
                () -> assertTrue(studio.hasName(newName)),
                () -> assertTrue(studio.pullEvents().stream().anyMatch(StudioRenamedEvent.class::isInstance))
        );
    }

    @Test
    void shouldUpdateBusinessHoursAndEmitChangedEvent() {
        Studio studio = new StudioTestBuilder().build();
        BusinessHours newHours = new BusinessHoursTestBuilder()
                .openAllDays(LocalTime.of(9, 0), LocalTime.of(18, 0))
                .build();

        studio.updateBusinessHours(newHours);

        assertAll(
                () -> assertTrue(studio.pullEvents().stream().anyMatch(BusinessHoursChangedEvent.class::isInstance))
        );
    }

    @Test
    void shouldUpdateCancellationPolicyAndEmitUpdatedEvent() {
        Studio studio = new StudioTestBuilder().build();
        ReservationCancellationPolicy newPolicy = new ReservationCancellationPolicy(Duration.ofHours(3));

        studio.updateCancellationPolicy(newPolicy);

        assertAll(
                () -> assertTrue(studio.pullEvents().stream().anyMatch(ReservationCancellationPolicyUpdatedEvent.class::isInstance))
        );
    }

    @Test
    void shouldUpdateContactDetails() {
        Studio studio = new StudioTestBuilder().build();
        ContactDetails newContactDetails = new ContactDetails("new Address", "987654321");

        studio.updateContactDetails(newContactDetails);

        assertAll(
                () -> assertTrue(studio.pullEvents().stream().anyMatch(ContactDetailsUpdatedEvent.class::isInstance))
        );
    }

    @Test
    void shouldActivateStudioAndEmitActivatedEvent() {
        Studio studio = new StudioTestBuilder().build();

        studio.activate();

        assertAll(
                () -> assertTrue(studio.isActive()),
                () -> assertTrue(studio.pullEvents().stream().anyMatch(StudioActivatedEvent.class::isInstance))
        );
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveStudio() {
        Studio studio = new StudioTestBuilder().build();

        studio.activate();

        assertThrows(StudioAlreadyActiveException.class, studio::activate);
    }

    @Test
    void shouldCloseActiveStudioAndEmitClosedEvent() {
        Studio studio = new StudioTestBuilder().build();

        studio.activate();
        studio.close();

        assertAll(
                () -> assertFalse(studio.isActive()),
                () -> assertTrue(studio.pullEvents().stream().anyMatch(StudioClosedEvent.class::isInstance))
        );
    }

    @Test
    void shouldReactivateClosedStudio() {
        Studio studio = new StudioTestBuilder().build();

        studio.activate();
        studio.close();
        studio.activate();

        assertAll(
                () -> assertTrue(studio.isActive())
        );
    }

    @Test
    void shouldThrowWhenClosingInactiveOrAlreadyClosedStudio() {
        Studio studio = new StudioTestBuilder().build();
        assertThrows(StudioAlreadyClosedException.class, studio::close);

        studio.activate();
        studio.close();

        assertThrows(StudioAlreadyClosedException.class, studio::close);
    }

    @Test
    void shouldReturnTrueWhenStudioIsActiveAndOpenForReservation() {
        Studio studio = new StudioTestBuilder().withBusinessHours(LocalTime.of(9, 0), LocalTime.of(11, 0)).build();

        studio.activate();

        assertTrue(studio.canAcceptReservationAt(DayOfWeek.MONDAY, LocalTime.of(10, 0)));
    }

    @Test
    void shouldReturnFalseWhenStudioIsInactive() {
        Studio studio = new StudioTestBuilder().build();

        assertFalse(studio.canAcceptReservationAt(DayOfWeek.MONDAY, LocalTime.of(10, 0)));
    }

    @Test
    void shouldReturnFalseWhenStudioIsClosed() {
        Studio studio = new StudioTestBuilder().build();

        studio.activate();
        studio.close();

        assertFalse(studio.canAcceptReservationAt(DayOfWeek.MONDAY, LocalTime.of(10, 0)));
    }

    @Test
    void shouldReturnFalseWhenReservationIsOutsideBusinessHours() {
        Studio studio = new StudioTestBuilder().withBusinessHours(LocalTime.of(7, 0), LocalTime.of(15, 0)).build();

        studio.activate();

        assertAll(
                () -> assertFalse(studio.canAcceptReservationAt(DayOfWeek.MONDAY, LocalTime.of(6, 0))),
                () -> assertFalse(studio.canAcceptReservationAt(DayOfWeek.MONDAY, LocalTime.of(15, 0)))
        );
    }

    @Test
    void shouldThrowOnBlankContactDetails() {
        assertThrows(ContactDetailsCannotBeBlankException.class, () -> new ContactDetails("  ", "123456789"));
    }

    @Test
    void shouldThrowOnNegativeCancellationPolicy() {
        assertThrows(InvalidCancellationThresholdException.class, () -> new ReservationCancellationPolicy(Duration.ofHours(-1)));
    }

    @Test
    void shouldThrowOnEmptyStudioName() {
        assertThrows(InvalidStudioNameException.class, () -> new StudioName("     "));
    }
}
