package com.reservation.useraccess.domain;

public class UserStatus {
    final boolean status;

    UserStatus(boolean status) {
        this.status = status;
    }

    static UserStatus active() {
        return new UserStatus(true);
    }
    static UserStatus inactive() {
        return new UserStatus(false);
    }

    UserStatus activate() {
        if (status) {
            throw new UserAlreadyActiveException();
        }
        return active();
    }

    UserStatus deactivate() {
        if (!status) {
            throw new UserAlreadyInactiveException();
        }
        return inactive();
    }

    boolean isActive() {
        return status;
    }
}
