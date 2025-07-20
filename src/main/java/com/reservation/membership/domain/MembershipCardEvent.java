package com.reservation.membership.domain;

public sealed interface MembershipCardEvent permits MembershipCardCreated, MembershipCardEntryUsed, MembershipCardFirstUsed {
}
