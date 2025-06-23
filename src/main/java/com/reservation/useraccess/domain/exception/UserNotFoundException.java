package com.reservation.useraccess.domain.exception;

import java.io.Serial;
import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "User with ID %s not found.";

    public UserNotFoundException(UUID userId) {
        super(String.format(MESSAGE_TEMPLATE, userId));
    }
}
