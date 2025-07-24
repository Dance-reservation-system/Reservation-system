package com.reservation.membership.domain.exception;

import java.time.LocalDate;

public class MembershipCardNotStartedException extends RuntimeException {

    private static final String MESSAGE = "Membership card not valid before %s";

    public MembershipCardNotStartedException(LocalDate date) {
        super(String.format(MESSAGE, date));
    }
}
