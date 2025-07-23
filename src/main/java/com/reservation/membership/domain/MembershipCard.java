package com.reservation.membership.domain;

import com.reservation.common.AggregateRoot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MembershipCard implements AggregateRoot<MembershipCardEvent> {
    private final MembershipCardId membershipCardId;
    private final ClientId clientId;
    private ValidEntries validEntries;

    private final List<MembershipCardEvent> membershipCardEvents = new ArrayList<>();

    private MembershipCard(MembershipCardId membershipCardId, ClientId clientId, ValidEntries validEntries) {
        this.membershipCardId = Objects.requireNonNull(membershipCardId);
        this.clientId = Objects.requireNonNull(clientId);
        this.validEntries = Objects.requireNonNull(validEntries);
    }

    static MembershipCard create(MembershipCardId membershipCardId, ClientId clientId, ValidEntries validEntries) {
        MembershipCard membershipCard = new MembershipCard(membershipCardId, clientId, validEntries);
        membershipCard.registerEvent(new MembershipCardCreated(membershipCardId, clientId));
        membershipCard.registerEvent(new MembershipCardActivated(membershipCardId, validEntries.validFrom()));
        return membershipCard;
    }

    MembershipCardId getMembershipCardId() {
        return this.membershipCardId;
    }

    ClientId getClientId() {
        return this.clientId;
    }

    void useEntry(LocalDate date) {
        this.validEntries = validEntries.useEntry(date);
        registerEvent(new MembershipCardEntryUsed(membershipCardId, validEntries.remaining()));
    }

    LocalDate getExpirationDate() {
        return validEntries.getExpirationDate();
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MembershipCard that = (MembershipCard) o;
        return Objects.equals(membershipCardId, that.membershipCardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(membershipCardId);
    }
}
