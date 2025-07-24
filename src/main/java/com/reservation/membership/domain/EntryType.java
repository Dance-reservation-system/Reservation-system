package com.reservation.membership.domain;

enum EntryType {
    ONE(1), FOUR(4), EIGHT(8), TWELVE(12);

    private final int value;

    EntryType(int value) {
        this.value = value;
    }

    int getValue() {
        return this.value;
    }
}
