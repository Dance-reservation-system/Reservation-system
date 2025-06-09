package com.reservation.useraccess.domain;

class UserAlreadyActiveException extends RuntimeException {
    /**
     * Constructs an exception indicating that the user is already active.
     */
    UserAlreadyActiveException() {
        super("User is already active.");
    }
}
