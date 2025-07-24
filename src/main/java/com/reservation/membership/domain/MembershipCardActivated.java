package com.reservation.membership.domain;

import java.time.Instant;
import java.time.LocalDate;

record MembershipCardActivated(MembershipCardId membershipCardId, LocalDate validFrom,
                               Instant activatedAt) implements MembershipCardEvent {
    MembershipCardActivated(MembershipCardId membershipCardId, LocalDate validFrom) {
        this(membershipCardId, validFrom, Instant.now());
    }
}
