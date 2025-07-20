package com.reservation.membership.domain;

import com.reservation.membership.domain.exception.MembershipCardNotValidException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        MembershipCard card = new MembershipCardTestBuilder().build();

        card.useEntry(today);

        List<MembershipCardEvent> events = card.pullEvents();

        MembershipCardFirstUsed firstUsed = findEvent(events, MembershipCardFirstUsed.class);
        MembershipCardEntryUsed entryUsed = findEvent(events, MembershipCardEntryUsed.class);

        assertAll(
                () -> assertEquals(today, firstUsed.firstUseDate()),
                () -> assertEquals(entryType.getValue() - 1, entryUsed.remaining()),
                () -> assertTrue(card.isValidAt(today))
        );
    }

    @Test
    void shouldNotAllowUsingEntryWhenExpired() {
        MembershipCard card = new MembershipCardTestBuilder().withEntryType(EntryType.ONE).build();
        card.useEntry(today);

        LocalDate afterExpiration = today.plusDays(31);

        assertThrows(MembershipCardNotValidException.class, () -> card.useEntry(afterExpiration));
    }

    @Test
    void shouldNotAllowUsingEntryWhenNoRemaining() {
        MembershipCard card = new MembershipCardTestBuilder().withEntryType(EntryType.ONE).build();
        card.useEntry(today);

        assertThrows(MembershipCardNotValidException.class, () -> card.useEntry(today));
    }

    @Test
    void shouldReturnCorrectExpirationDate() {
        MembershipCard card = new MembershipCardTestBuilder().build();

        assertNull(card.getExpirationDate());

        card.useEntry(today);

        assertEquals(today.plusDays(30), card.getExpirationDate());
    }

    @Test
    void shouldBeValidExactlyOnExpirationDate() {
        MembershipCard card = new MembershipCardTestBuilder().build();
        card.useEntry(today);

        LocalDate expirationDate = today.plusDays(30);

        assertTrue(card.isValidAt(expirationDate));
    }

    private <T> T findEvent(List<MembershipCardEvent> events, Class<T> clazz) {
        return events.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected event of type: " + clazz.getSimpleName()));
    }
}
