package com.reservation.membership.domain;

import com.reservation.membership.domain.exception.MembershipCardNotValidException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MembershipCardTest {
    private final EntryType entryType = EntryType.FOUR;
    private final LocalDate today = LocalDate.now();

    @Test
    void shouldCreateMembershipCardAndEmitCreatedEvent() {
        MembershipCard card = new MembershipCardTestBuilder().build();

        List<MembershipCardEvent> events = card.pullEvents();

        MembershipCardCreated createdEvent = findEvent(events, MembershipCardCreated.class);

        assertAll(
                () -> assertNotNull(createdEvent),
                () -> assertNotNull(createdEvent.createdAt())
        );
    }

    @Test
    void shouldUseEntryAndEmitFirstUseAndEntryUsedEvents() {
        MembershipCard card = new MembershipCardTestBuilder().build();

        card.useEntry(today);

        List<MembershipCardEvent> events = card.pullEvents();

        MembershipCardFirstUsed firstUsed = findEvent(events, MembershipCardFirstUsed.class);
        MembershipCardEntryUsed entryUsed = findEvent(events, MembershipCardEntryUsed.class);

        assertAll(
                () -> assertEquals(today, firstUsed.firstUseDate()),
                () -> assertEquals(entryType.getValue() - 1, entryUsed.remaining()),
                () -> assertDoesNotThrow(() -> card.assertValidAt(today))
        );
    }

    @Test
    void shouldReturnNullExpirationDateBeforeFirstUse() {
        MembershipCard card = new MembershipCardTestBuilder().withValidityDays(60).build();

        assertNull(card.getExpirationDate());
    }

    @Test
    void shouldReturnCorrectExpirationDateAfterFirstUse() {
        MembershipCard card = new MembershipCardTestBuilder().withValidityDays(60).build();

        card.useEntry(today);

        assertEquals(today.plusDays(60), card.getExpirationDate());
    }

    @Test
    void shouldBeValidOnExpirationDate() {
        MembershipCard card = new MembershipCardTestBuilder().build();
        card.useEntry(today);

        LocalDate expirationDate = today.plusDays(30);

        assertDoesNotThrow(() -> card.assertValidAt(expirationDate));
    }

    @Test
    void shouldThrowWhenValidatingBeforeFirstUse() {
        MembershipCard card = new MembershipCardTestBuilder().build();

        assertThrows(MembershipCardNotValidException.class, () -> card.assertValidAt(today));
    }

    @Test
    void shouldNotAllowUsingEntryWhenExpired() {
        MembershipCard card = new MembershipCardTestBuilder().withEntryType(EntryType.ONE).build();
        card.useEntry(today);

        LocalDate afterExpiration = today.plusDays(31);

        assertThrows(MembershipCardNotValidException.class, () -> card.useEntry(afterExpiration));
    }

    @Test
    void shouldNotAllowUsingEntryWhenNoRemainingEntries() {
        MembershipCard card = new MembershipCardTestBuilder().withEntryType(EntryType.ONE).build();
        card.useEntry(today);

        assertThrows(MembershipCardNotValidException.class, () -> card.useEntry(today));
    }

    private <T> T findEvent(List<MembershipCardEvent> events, Class<T> clazz) {
        return events.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected event of type: " + clazz.getSimpleName()));
    }
}
