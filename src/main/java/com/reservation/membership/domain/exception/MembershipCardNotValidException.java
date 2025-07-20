package com.reservation.membership.domain.exception;

import java.io.Serial;

public class MembershipCardNotValidException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MembershipCardNotValidException(String message) {
        super(message);
    }
}
