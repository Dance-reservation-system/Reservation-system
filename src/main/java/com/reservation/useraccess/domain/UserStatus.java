package com.reservation.useraccess.domain;

public class UserStatus {
    final boolean status;

    /**
     * Creates a new UserStatus with the specified activation state.
     *
     * @param status true for active, false for inactive
     */
    UserStatus(boolean status) {
        this.status = status;
    }

    /**
     * Creates a new UserStatus instance representing an active user.
     *
     * @return a UserStatus with active status
     */
    static UserStatus active() {
        return new UserStatus(true);
    }
    /**
     * Creates a new UserStatus instance representing an inactive user.
     *
     * @return a UserStatus with inactive status
     */
    static UserStatus inactive() {
        return new UserStatus(false);
    }

    /**
     * Returns a new UserStatus instance representing an active user.
     *
     * @return a UserStatus with active status
     * @throws UserAlreadyActiveException if the user is already active
     */
    UserStatus activate() {
        if (status) {
            throw new UserAlreadyActiveException();
        }
        return active();
    }

    /****
     * Returns a new UserStatus instance representing an inactive state.
     *
     * @return a UserStatus with inactive status
     * @throws UserAlreadyInactiveException if the user is already inactive
     */
    UserStatus deactivate() {
        if (!status) {
            throw new UserAlreadyInactiveException();
        }
        return inactive();
    }

    /**
     * Returns whether the user is currently active.
     *
     * @return {@code true} if the user is active; {@code false} otherwise
     */
    boolean isActive() {
        return status;
    }
}
