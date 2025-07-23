package com.reservation.membership.domain;

import java.time.LocalDate;

public class MembershipCardTestBuilder {
    private MembershipCardId membershipCardId = MembershipCardId.next();
    private ClientId clientId = ClientId.next();
    private EntryType entryType = EntryType.FOUR;
    private LocalDate validFrom = LocalDate.now();
    private MembershipCardType membershipCardType = MembershipCardType.MONTH;

    MembershipCardTestBuilder withMembershipCardId(MembershipCardId membershipCardId) {
        this.membershipCardId = membershipCardId;
        return this;
    }

    MembershipCardTestBuilder withClientId(ClientId clientId) {
        this.clientId = clientId;
        return this;
    }

    MembershipCardTestBuilder withEntryType(EntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    MembershipCardTestBuilder withValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    MembershipCardTestBuilder withType(MembershipCardType membershipCardType) {
        this.membershipCardType = membershipCardType;
        return this;
    }

    public MembershipCard build() {
        return MembershipCard.create(membershipCardId, clientId, new ValidEntries(entryType, entryType.getValue(), validFrom, membershipCardType));
    }
}
