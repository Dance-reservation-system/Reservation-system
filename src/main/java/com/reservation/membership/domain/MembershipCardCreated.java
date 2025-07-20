package com.reservation.membership.domain;

import java.time.Instant;

record MembershipCardCreated(MembershipCardId membershipCardId, ClientId clientId, Instant createdAt) implements MembershipCardEvent {
    public MembershipCardCreated(MembershipCardId membershipCardId, ClientId clientId) {
        this(membershipCardId, clientId, Instant.now());
    }
}
