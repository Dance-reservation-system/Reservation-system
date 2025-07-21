package com.reservation.membership.domain;

public class MembershipCardTestBuilder {
    private MembershipCardId membershipCardId = MembershipCardId.next();
    private ClientId clientId = ClientId.next();
    private EntryType entryType = EntryType.FOUR;
    private int validityDays = 30;

    public MembershipCardTestBuilder withMembershipCardId(MembershipCardId membershipCardId) {
        this.membershipCardId = membershipCardId;
        return this;
    }

    public MembershipCardTestBuilder withClientId(ClientId clientId) {
        this.clientId = clientId;
        return this;
    }

    public MembershipCardTestBuilder withEntryType(EntryType type) {
        this.entryType = type;
        return this;
    }

    public MembershipCardTestBuilder withValidityDays(int days) {
        this.validityDays = days;
        return this;
    }

    public MembershipCard build() {
        return MembershipCard.create(membershipCardId, clientId, entryType, validityDays);
    }
}
