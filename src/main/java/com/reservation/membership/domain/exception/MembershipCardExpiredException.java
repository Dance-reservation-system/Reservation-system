package com.reservation.membership.domain.exception;

import java.time.LocalDate;

public class MembershipCardExpiredException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Membership card expired on %s";

    public MembershipCardExpiredException(LocalDate date) {
        super(String.format(MESSAGE_TEMPLATE, date));
    }
}
