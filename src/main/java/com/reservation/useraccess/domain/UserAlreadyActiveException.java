package com.reservation.useraccess.domain;

class UserAlreadyActiveException extends RuntimeException {
    UserAlreadyActiveException() {
        super("User is already active.");
    }
}
