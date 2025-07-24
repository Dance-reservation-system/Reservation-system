package com.reservation.membership.domain;

import com.reservation.membership.domain.exception.MembershipCardExpiredException;
import com.reservation.membership.domain.exception.MembershipCardNotStartedException;
import com.reservation.membership.domain.exception.NoRemainingEntriesException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MembershipCardTest {
    private final EntryType entryType = EntryType.FOUR;
    private final LocalDate today = LocalDate.now();

    @Test
    void shouldCreateMembershipCardAndEmitEvent() {
        MembershipCard card = new MembershipCardTestBuilder().build();

        List<MembershipCardEvent> events = card.pullEvents();

        MembershipCardCreated createdEvent = findEvent(events, MembershipCardCreated.class);

        assertAll(
                () -> assertNotNull(createdEvent),
                () -> assertNotNull(createdEvent.createdAt())
        );
    }

    @Test
    void shouldUseEntryAndEmitEvents() {
        MembershipCard card = new MembershipCardTestBuilder().withEntryType(entryType).build();

        card.useEntry(today);

        List<MembershipCardEvent> events = card.pullEvents();

        MembershipCardActivated activated = findEvent(events, MembershipCardActivated.class);
        MembershipCardEntryUsed entryUsed = findEvent(events, MembershipCardEntryUsed.class);

        assertAll(
                () -> assertEquals(today, activated.validFrom()),
                () -> assertEquals(entryType.getValue() - 1, entryUsed.remaining())
        );
    }

    @Test
    void shouldReturnCorrectExpirationDateAfterFirstUse() {
        MembershipCard card = new MembershipCardTestBuilder().withType(MembershipCardType.MONTH).build();

        card.useEntry(today);

        LocalDate expirationDate = today.plusDays(MembershipCardType.MONTH.getDurationInDays());

        assertEquals(expirationDate, card.getExpirationDate());
    }

    @Test
    void shouldThrowWhenUsingEntryAfterExpiration() {
        MembershipCard card = new MembershipCardTestBuilder().withType(MembershipCardType.ONE_WEEK).build();

        card.useEntry(today);

        LocalDate afterExpiration = today.plusDays(MembershipCardType.ONE_WEEK.getDurationInDays() + 1);

        assertThrows(MembershipCardExpiredException.class, () -> card.useEntry(afterExpiration));
    }

    @Test
    void shouldThrowWhenUsingEntryBeforeValidFromDate() {
        MembershipCard card = new MembershipCardTestBuilder().build();

        card.useEntry(today);

        LocalDate beforeValidFrom = today.minusDays(1);

        assertThrows(MembershipCardNotStartedException.class, () -> card.useEntry(beforeValidFrom));
    }

    @Test
    void shouldAllowUseEntryOnExpirationDate() {
        MembershipCard card = new MembershipCardTestBuilder().withType(MembershipCardType.ONE_WEEK).build();

        card.useEntry(today);

        LocalDate expirationDate = today.plusDays(MembershipCardType.ONE_WEEK.getDurationInDays());

        assertDoesNotThrow(() -> card.useEntry(expirationDate));
    }

    @Test
    void shouldThrowWhenNoRemainingEntries() {
        MembershipCard card = new MembershipCardTestBuilder().withEntryType(EntryType.ONE).build();

        card.useEntry(today);

        assertThrows(NoRemainingEntriesException.class, () -> card.useEntry(today));
    }

    private <T> T findEvent(List<MembershipCardEvent> events, Class<T> clazz) {
        return events.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected event of type: " + clazz.getSimpleName()));
    }
}
