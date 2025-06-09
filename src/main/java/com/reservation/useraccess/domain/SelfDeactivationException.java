package com.reservation.useraccess.domain;

import java.util.UUID;

class SelfDeactivationException extends RuntimeException {
    /****
     * Constructs a SelfDeactivationException indicating that the specified user cannot deactivate their own account.
     *
     * @param userId the UUID of the user attempting self-deactivation
     */
    SelfDeactivationException(UUID userId) {
        super("User with ID " + userId + " cannot deactivate themselves.");
    }
}
