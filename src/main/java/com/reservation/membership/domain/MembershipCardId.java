package com.reservation.membership.domain;

import java.util.Objects;
import java.util.UUID;

record MembershipCardId(UUID value) {
    MembershipCardId {
        Objects.requireNonNull(value);
    }

    static MembershipCardId next() {
        return new MembershipCardId(UUID.randomUUID());
    }
}
