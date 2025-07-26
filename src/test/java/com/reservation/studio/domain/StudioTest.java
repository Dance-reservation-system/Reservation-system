package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.ContactDetailsCannotBeEmptyException;
import com.reservation.studio.domain.exception.InvalidCancellationThresholdException;
import com.reservation.studio.domain.exception.InvalidStudioNameException;
import com.reservation.studio.domain.exception.StudioAlreadyActiveException;
import com.reservation.studio.domain.exception.StudioAlreadyClosedException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudioTest {
    @Test
    void shouldCreateStudioAndEmitCreatedEvent() {
        Studio studio = new StudioTestBuilder().build();

        assertAll(
                () -> assertFalse(studio.isActive()),
                () -> assertThat(studio.pullEvents()).anyMatch(e -> e instanceof StudioCreated)
        );
    }
    @Test
    void shouldRenameStudioAndEmitRenamedEvent() {
        Studio studio = new StudioTestBuilder().build();
        StudioName newName = new StudioName("New Studio Name");

        studio.rename(newName);

        assertAll(
                () -> assertTrue(studio.hasName(newName)),
                () -> assertThat(studio.pullEvents()).anyMatch(e -> e instanceof StudioRenamed)
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
                () -> assertThat(studio.pullEvents()).anyMatch(e -> e instanceof BusinessHoursChanged)
        );
    }

    @Test
    void shouldUpdateCancellationPolicyAndEmitUpdatedEvent() {
        Studio studio = new StudioTestBuilder().build();
        CancellationPolicy newPolicy = new CancellationPolicy(Duration.ofHours(3));

        studio.updateCancellationPolicy(newPolicy);

        assertAll(
                () -> assertThat(studio.pullEvents()).anyMatch(e -> e instanceof CancellationPolicyUpdated)
        );
    }

    @Test
    void shouldActivateStudioAndEmitActivatedEvent() {
        Studio studio = new StudioTestBuilder().build();

        studio.activate();

        assertAll(
                () -> assertTrue(studio.isActive()),
                () -> assertThat(studio.pullEvents()).anyMatch(e -> e instanceof StudioActivated)
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
                () -> assertThat(studio.pullEvents()).anyMatch(e -> e instanceof StudioClosed)
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
    void shouldThrowOnEmptyContactDetails(){
        assertThrows(ContactDetailsCannotBeEmptyException.class, () -> new ContactDetails("", "123456789"));
    }

    @Test
    void shouldThrowOnNegativeCancellationPolicy(){
        assertThrows(InvalidCancellationThresholdException.class, () -> new CancellationPolicy(Duration.ofHours(-1)));
    }

    @Test
    void shouldThrowOnEmptyStudioName(){
        assertThrows(InvalidStudioNameException.class, () -> new StudioName("     "));
    }
}
