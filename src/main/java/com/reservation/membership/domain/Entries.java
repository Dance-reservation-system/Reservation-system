package com.reservation.membership.domain;

import com.reservation.membership.domain.exception.InvalidEntriesCountException;
import com.reservation.membership.domain.exception.NoRemainingEntriesException;

record Entries(EntryType type, int remaining) {
    public Entries {
        if (remaining < 0 || remaining > type.getValue()) {
            throw new InvalidEntriesCountException(type.getValue());
        }
    }

    public Entries useEntry() {
        if (noRemainingEntries()) {
            throw new NoRemainingEntriesException();
        }
        return new Entries(type, remaining - 1);
    }

    public boolean noRemainingEntries() {
        return remaining == 0;
    }

    public int getMaxEntries() {
        return type.getValue();
    }
}
