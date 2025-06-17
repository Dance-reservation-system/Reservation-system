package com.reservation.useraccess.domain;

import com.reservation.useraccess.domain.exception.SelfDeactivationException;
import com.reservation.useraccess.domain.exception.UserAlreadyActiveException;
import com.reservation.useraccess.domain.exception.UserAlreadyInactiveException;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class SystemUser {
    private boolean active;
    private final UUID keycloakUserId;
    private Set<UserRole> roles;

    public SystemUser(UUID keycloakUserId, boolean active, Set<UserRole> roles) {
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId);
        this.roles = Set.copyOf(Objects.requireNonNull(roles));
        if (this.roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }
        this.active = active;
    }

    public Set<UserRole> getRoles() {
        return Set.copyOf(roles);
    }

    public boolean isActive() {
        return active;
    }

    public UUID getKeycloakUserId() {
        return keycloakUserId;
    }

    public void activate() {
        if (active) {
            throw new UserAlreadyActiveException(keycloakUserId);
        }
        active = true;
    }

    public void deactivate(UUID loggedInUserId) {
        if (!active) {
            throw new UserAlreadyInactiveException(keycloakUserId);
        }
        if (Objects.equals(keycloakUserId, loggedInUserId)) {
            throw new SelfDeactivationException(loggedInUserId);
        }
        active = false;
    }

    public void replaceRoles(Set<UserRole> roles) {
        Set<UserRole> copiedRoles = Set.copyOf(Objects.requireNonNull(roles, "Roles cannot be null"));
        if (copiedRoles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }
        this.roles = copiedRoles;
    }
}
