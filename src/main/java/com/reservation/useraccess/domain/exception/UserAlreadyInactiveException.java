package com.reservation.useraccess.domain.exception;

import java.io.Serial;
import java.util.UUID;

public final class UserAlreadyInactiveException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "User with ID %s is already inactive.";

    public UserAlreadyInactiveException() {
        super("User is already inactive.");
    }

    public UserAlreadyInactiveException(UUID userId) {
        super(String.format(MESSAGE_TEMPLATE, userId));
    }
}
