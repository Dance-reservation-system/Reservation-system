package com.reservation.useraccess.domain;

class UserAlreadyInactiveException extends RuntimeException {
    UserAlreadyInactiveException() {
        super("User is already inactive.");
    }
}
