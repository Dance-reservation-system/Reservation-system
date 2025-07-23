package com.reservation.membership.domain;

enum MembershipCardType {
    ONE_WEEK(7),
    TWO_WEEKS(14),
    MONTH(30);

    private final int value;

    MembershipCardType(int value) {
        this.value = value;
    }

    int getValue() {
        return this.value;
    }
}
