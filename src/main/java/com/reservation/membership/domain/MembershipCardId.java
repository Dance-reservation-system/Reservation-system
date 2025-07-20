package com.reservation.membership.domain;

import java.util.Objects;
import java.util.UUID;

record MembershipCardId(UUID value) {
    public MembershipCardId {
        Objects.requireNonNull(value);
    }

    public static MembershipCardId next(){
        return new MembershipCardId(UUID.randomUUID());
    }
}
