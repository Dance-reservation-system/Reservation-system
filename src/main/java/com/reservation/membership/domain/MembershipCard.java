package com.reservation.membership.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.membership.domain.exception.InvalidValidityDaysException;
import com.reservation.membership.domain.exception.MembershipCardNotValidException;
import com.reservation.membership.domain.exception.NoRemainingEntriesException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MembershipCard implements AggregateRoot<MembershipCardEvent> {
    private final MembershipCardId membershipCardId;
    private final ClientId clientId;
    private Entries entries;
    private LocalDate firstUseDate;
    private final int validityDays;

    private final ArrayList<MembershipCardEvent> membershipCardEvents = new ArrayList<>();

    private MembershipCard(MembershipCardId membershipCardId, ClientId clientId, EntryType entryType, int validityDays) {
        this.membershipCardId = Objects.requireNonNull(membershipCardId);
        this.clientId = Objects.requireNonNull(clientId);
        if (validityDays <= 0) {
            throw new InvalidValidityDaysException();
        }
        this.validityDays = validityDays;
        Objects.requireNonNull(entryType);
        this.entries = new Entries(entryType, entryType.getValue());
        this.firstUseDate = null;
    }

    public static MembershipCard create(MembershipCardId membershipCardId, ClientId clientId, EntryType entryType, int validityDays) {
        MembershipCard membershipCard = new MembershipCard(membershipCardId, clientId, entryType, validityDays);
        membershipCard.registerEvent(new MembershipCardCreated(membershipCardId, clientId));
        return membershipCard;
    }

    public MembershipCardId getMembershipCardId() {
        return this.membershipCardId;
    }

    public ClientId getClientId() {
        return this.clientId;
    }

    public void useEntry(LocalDate date) {
        if (entries.noRemainingEntries()) {
            throw new NoRemainingEntriesException();
        }
        if (firstUseDate == null) {
            firstUseDate = date;
            registerEvent(new MembershipCardFirstUsed(membershipCardId, firstUseDate));
        }
        assertValidAt(date);
        this.entries = entries.useEntry();
        registerEvent(new MembershipCardEntryUsed(membershipCardId, entries.remaining()));
    }

    public void assertValidAt(LocalDate date) {
        if (entries.noRemainingEntries()) {
            throw new NoRemainingEntriesException();
        }
        if (firstUseDate == null) {
            throw new MembershipCardNotValidException("Membership card has not been activated yet.");
        }
        LocalDate expirationDate = firstUseDate.plusDays(validityDays);
        if (date.isAfter(expirationDate)) {
            throw new MembershipCardNotValidException("Membership card expired on " + expirationDate);
        }
    }

    public LocalDate getExpirationDate() {
        return firstUseDate != null ? firstUseDate.plusDays(validityDays) : null;
    }

    @Override
    public List<MembershipCardEvent> pullEvents() {
        List<MembershipCardEvent> copyEvents = List.copyOf(membershipCardEvents);
        membershipCardEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(MembershipCardEvent event) {
        membershipCardEvents.add(event);
    }
}
