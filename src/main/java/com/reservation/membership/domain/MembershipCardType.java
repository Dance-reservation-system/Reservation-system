package com.reservation.membership.domain;

enum MembershipCardType {
    ONE_WEEK(7),
    TWO_WEEKS(14),
    MONTH(30);

    private final int durationInDays;

    MembershipCardType(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    int getDurationInDays() {
        return this.durationInDays;
    }
}
