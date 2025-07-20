package com.reservation.membership.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.membership.domain.exception.MembershipCardNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MembershipCard implements AggregateRoot<MembershipCardEvent> {
    private final MembershipCardId membershipCardId;
    private final ClientId clientId;
    private Entries entries;
    private LocalDate firstUseDate;
    private final int validityDays = 30;

    private final ArrayList<MembershipCardEvent> membershipCardEvents = new ArrayList<>();

    private MembershipCard(MembershipCardId membershipCardId, ClientId clientId, EntryType entryType) {
        this.membershipCardId = Objects.requireNonNull(membershipCardId);
        this.clientId = Objects.requireNonNull(clientId);
        Objects.requireNonNull(entryType);
        this.entries = new Entries(entryType, entryType.getValue());
        this.firstUseDate = null;
    }

    public static MembershipCard create(MembershipCardId membershipCardId, ClientId clientId, EntryType entryType) {
        MembershipCard membershipCard = new MembershipCard(membershipCardId, clientId, entryType);
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
        if (firstUseDate == null) {
            firstUseDate = date;
            registerEvent(new MembershipCardFirstUsed(membershipCardId, firstUseDate));
        }
        if (!isValidAt(date)) {
            throw new MembershipCardNotValidException("Membership card is not valid at " + date);
        }
        this.entries = entries.useEntry();
        registerEvent(new MembershipCardEntryUsed(membershipCardId, entries.remaining()));
    }

    public boolean isValidAt(LocalDate date) {
        return entries.hasEntries() && firstUseDate != null && !date.isAfter(firstUseDate.plusDays(validityDays));
    }

    public LocalDate getExpirationDate() {
        return firstUseDate != null ? firstUseDate.plusDays(validityDays) : null;
    }

    @Override
    public List<MembershipCardEvent> pullEvents() {
        List<MembershipCardEvent> copyEvents = new ArrayList<>(this.membershipCardEvents);
        this.membershipCardEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(MembershipCardEvent event) {
        membershipCardEvents.add(event);
    }
}
