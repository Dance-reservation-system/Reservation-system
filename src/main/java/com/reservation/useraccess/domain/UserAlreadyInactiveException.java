package com.reservation.useraccess.domain;

class UserAlreadyInactiveException extends RuntimeException {
    /**
     * Constructs an exception indicating that the user is already inactive.
     */
    UserAlreadyInactiveException() {
        super("User is already inactive.");
    }
}
