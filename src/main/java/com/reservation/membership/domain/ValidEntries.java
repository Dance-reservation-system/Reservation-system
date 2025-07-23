package com.reservation.membership.domain;

import com.reservation.membership.domain.exception.InvalidEntriesCountException;
import com.reservation.membership.domain.exception.MembershipCardExpiredException;
import com.reservation.membership.domain.exception.MembershipCardNotStartedException;
import com.reservation.membership.domain.exception.NoRemainingEntriesException;

import java.time.LocalDate;
import java.util.Objects;

record ValidEntries(EntryType type, int remaining, LocalDate validFrom, MembershipCardType membershipCardType) {
    ValidEntries {
        if (remaining < 0 || remaining > type.getValue()) {
            throw new InvalidEntriesCountException(type.getValue());
        }
        Objects.requireNonNull(validFrom);
    }

    ValidEntries useEntry(LocalDate atDate) {
        assertValid(atDate);
        if (noRemainingEntries()) {
            throw new NoRemainingEntriesException();
        }
        return new ValidEntries(type, remaining - 1, validFrom, membershipCardType);
    }

    void assertValid(LocalDate atDate) {
        if (atDate.isBefore(validFrom)) {
            throw new MembershipCardNotStartedException(validFrom);
        }
        if (atDate.isAfter(getExpirationDate())) {
            throw new MembershipCardExpiredException(getExpirationDate());
        }
    }

    LocalDate getExpirationDate() {
        return validFrom.plusDays(membershipCardType.getValue());
    }

    boolean noRemainingEntries() {
        return remaining == 0;
    }

    int getMaxEntries() {
        return type.getValue();
    }
}
