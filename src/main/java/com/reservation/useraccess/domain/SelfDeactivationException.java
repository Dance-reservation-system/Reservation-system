package com.reservation.useraccess.domain;

import java.util.UUID;

class SelfDeactivationException extends RuntimeException {
    SelfDeactivationException(UUID userId) {
        super("User with ID " + userId + " cannot deactivate themselves.");
    }
}
