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

    public SystemUser(UUID keycloakUserId, UserRole role) {
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "Keycloak user ID cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
        this.status = UserStatus.active();
    }

    public boolean isActive() {
        return this.status.isActive();
    }

    public void activate() {
        this.status = this.status.activate();
    }

    public void deactivate(UUID loggedInUserId) {
        if (Objects.equals(this.keycloakUserId, loggedInUserId)) {
            throw new SelfDeactivationException(loggedInUserId);
        }
        this.status = this.status.deactivate();
    }

    public void changeRole(UserRole newRole) {
        this.role = Objects.requireNonNull(newRole, "Role cannot be null");
    }
}
