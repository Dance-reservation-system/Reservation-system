package com.reservation.membership.domain;

import com.reservation.membership.domain.exception.InvalidEntriesCountException;

record Entries(EntryType type, int remaining) {
    public Entries {
        if (remaining < 0 || remaining > type.getValue()) {
            throw new InvalidEntriesCountException(type.getValue());
        }
    }

    public Entries useEntry() {
        return new Entries(type, remaining - 1);
    }

    public boolean hasEntries() {
        return remaining > 0;
    }

    public int getMaxEntries() {
        return type.getValue();
    }
}
