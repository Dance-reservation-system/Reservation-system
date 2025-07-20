package com.reservation.membership.domain;

import java.time.Instant;

record MembershipCardEntryUsed(MembershipCardId membershipCardId, int remaining,
                               Instant usedAt) implements MembershipCardEvent {
    public MembershipCardEntryUsed(MembershipCardId membershipCardId, int remaining) {
        this(membershipCardId, remaining, Instant.now());
    }
}
