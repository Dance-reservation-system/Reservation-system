package com.reservation.useraccess;

import com.reservation.useraccess.domain.SystemUser;
import com.reservation.useraccess.domain.UserRole;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SystemUserTest {

    @Test
    void shouldCreateSystemUserWithValidRole() {
        UUID keycloakId = UUID.randomUUID();
        SystemUser user = new SystemUser(keycloakId, UserRole.OWNER);

        assertThat(user.getKeycloakUserId()).isEqualTo(keycloakId);
        assertThat(user.getRole()).isEqualTo(UserRole.OWNER);
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void shouldThrowExceptionForInvalidRole() {
        assertThatThrownBy(() -> new SystemUser(UUID.randomUUID(), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotAllowDeactivationOfSelf() {
        UUID userId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId, UserRole.OWNER);

        assertThatThrownBy(() -> user.deactivate(userId))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldDeactivateAndActivateUser() {
        UUID userId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId, UserRole.CLIENT);

        user.deactivate(adminId);
        assertThat(user.isActive()).isFalse();

        user.activate();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void shouldChangeRoleIfValid() {
        SystemUser user = new SystemUser(UUID.randomUUID(), UserRole.CLIENT);
        user.changeRole(UserRole.OWNER);

        assertThat(user.getRole()).isEqualTo(UserRole.OWNER);
    }

    @Test
    void shouldNotChangeToInvalidRole() {
        SystemUser user = new SystemUser(UUID.randomUUID(), UserRole.CLIENT);

        assertThatThrownBy(() -> user.changeRole(null))
                .isInstanceOf(NullPointerException.class);
    }
}
