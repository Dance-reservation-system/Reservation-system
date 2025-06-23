package com.reservation.useraccess.domain.exception;

import java.io.Serial;
import java.util.UUID;

public class UserAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "User with ID %s already exists.";

    public UserAlreadyExistsException(UUID keycloakUserId) {
        super(String.format(MESSAGE_TEMPLATE, keycloakUserId));
    }
}
