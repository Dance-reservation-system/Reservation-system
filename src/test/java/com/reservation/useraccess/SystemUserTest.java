package com.reservation.useraccess;

import com.reservation.useraccess.domain.exception.SelfDeactivationException;
import com.reservation.useraccess.domain.SystemUser;
import com.reservation.useraccess.domain.UserRole;
import com.reservation.useraccess.domain.exception.UserAlreadyActiveException;
import com.reservation.useraccess.domain.exception.UserAlreadyInactiveException;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SystemUserTest {

    @Test
    void shouldCreateSystemUserWithValidRole() {
        UUID keycloakId = UUID.randomUUID();
        SystemUser user = new SystemUser(keycloakId, Set.of(UserRole.OWNER));

        assertThat(user.getKeycloakUserId()).isEqualTo(keycloakId);
        assertThat(user.getRoles().contains(UserRole.OWNER)).isTrue();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void shouldThrowExceptionForInvalidRole() {
        assertThatThrownBy(() -> new SystemUser(UUID.randomUUID(), Set.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAllowDeactivationOfSelf() {
        UUID userId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId, Set.of(UserRole.OWNER));

        assertThatThrownBy(() -> user.deactivate(userId))
                .isInstanceOf(SelfDeactivationException.class);
    }

    @Test
    void shouldDeactivateAndActivateUser() {
        UUID userId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId, Set.of(UserRole.CLIENT));

        user.deactivate(adminId);
        assertThat(user.isActive()).isFalse();

        user.activate();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void shouldChangeRoleIfValid() {
        SystemUser user = new SystemUser(UUID.randomUUID(), Set.of(UserRole.CLIENT));
        user.replaceRoles(Set.of(UserRole.OWNER));

        assertThat(user.getRoles()).contains(UserRole.OWNER);
    }

    @Test
    void shouldThrowExceptionWhenActivatingAlreadyActiveUser() {
        UUID userId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId, Set.of(UserRole.CLIENT));

        assertThatThrownBy(user::activate)
                .isInstanceOf(UserAlreadyActiveException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingAlreadyInactiveUser() {
        UUID userId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId, Set.of(UserRole.CLIENT));

        user.deactivate(adminId);

        assertThatThrownBy(() -> user.deactivate(adminId))
                .isInstanceOf(UserAlreadyInactiveException.class);
    }
    @Test
    void rolesShouldBeUnmodifiable() {
        SystemUser user = new SystemUser(UUID.randomUUID(), Set.of(UserRole.CLIENT));
        assertThatThrownBy(() -> user.getRoles().add(UserRole.OWNER))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
