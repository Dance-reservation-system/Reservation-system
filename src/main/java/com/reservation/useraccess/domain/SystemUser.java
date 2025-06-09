package com.reservation.useraccess.domain;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

public class SystemUser {
    @Getter
    private final UUID keycloakUserId;
    @Getter
    private UserRole role;
    private UserStatus status;

    /**
     * Constructs a new SystemUser with the specified Keycloak user ID and role.
     *
     * @param keycloakUserId the unique identifier for the user; must not be null
     * @param role the user's role; must not be null
     *
     * @throws NullPointerException if keycloakUserId or role is null
     */
    public SystemUser(UUID keycloakUserId, UserRole role) {
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "Keycloak user ID cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
        this.status = UserStatus.active();
    }

    /**
     * Checks if the user is currently active.
     *
     * @return true if the user's status is active; false otherwise
     */
    public boolean isActive() {
        return this.status.isActive();
    }

    /****
     * Sets the user's status to active.
     */
    public void activate() {
        this.status = this.status.activate();
    }

    /**
     * Deactivates the user unless the provided user ID matches the user's own ID.
     *
     * @param loggedInUserId the ID of the user attempting the deactivation
     * @throws SelfDeactivationException if the user attempts to deactivate themselves
     */
    public void deactivate(UUID loggedInUserId) {
        if (Objects.equals(this.keycloakUserId, loggedInUserId)) {
            throw new SelfDeactivationException(loggedInUserId);
        }
        this.status = this.status.deactivate();
    }

    /**
     * Updates the user's role to the specified new role.
     *
     * @param newRole the new role to assign to the user; must not be null
     * @throws NullPointerException if newRole is null
     */
    public void changeRole(UserRole newRole) {
        this.role = Objects.requireNonNull(newRole, "Role cannot be null");
    }
}
