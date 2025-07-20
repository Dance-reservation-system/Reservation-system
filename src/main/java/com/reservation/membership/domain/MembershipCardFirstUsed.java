package com.reservation.membership.domain;

import java.time.Instant;
import java.time.LocalDate;

record MembershipCardFirstUsed(MembershipCardId membershipCardId, LocalDate firstUseDate, Instant firstUsedAt) implements MembershipCardEvent {
    public MembershipCardFirstUsed(MembershipCardId membershipCardId, LocalDate firstUseDate) {
        this(membershipCardId, firstUseDate, Instant.now());
    }
}
