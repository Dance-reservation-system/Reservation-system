package com.reservation.membership.domain;

public class MembershipCardTestBuilder {
    private final MembershipCardId membershipCardId = MembershipCardId.next();
    private final ClientId clientId = ClientId.next();
    private EntryType entryType = EntryType.FOUR;

    public MembershipCardTestBuilder withEntryType(EntryType type) {
        this.entryType = type;
        return this;
    }

    public MembershipCard build() {
        return MembershipCard.create(membershipCardId, clientId, entryType);
    }
}
