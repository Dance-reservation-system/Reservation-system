package com.reservation.useraccess;

import com.reservation.useraccess.domain.SystemUser;
import com.reservation.useraccess.domain.UserRole;
import com.reservation.useraccess.domain.exception.SelfDeactivationException;
import com.reservation.useraccess.domain.exception.UserAlreadyActiveException;
import com.reservation.useraccess.domain.exception.UserAlreadyInactiveException;
import com.reservation.useraccess.domain.exception.UserRolesEmptyException;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SystemUserTest {

    @Test
    void shouldCreateSystemUserWithValidRole() {
        UUID keycloakId = UUID.randomUUID();
        SystemUser user = new SystemUser(keycloakId, true, Set.of(UserRole.OWNER));

        assertThat(user.getKeycloakUserId()).isEqualTo(keycloakId);
        assertThat(user.getRoles().contains(UserRole.OWNER)).isTrue();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void shouldThrowExceptionForInvalidRole() {
        assertThrows(UserRolesEmptyException.class,
                () -> new SystemUser(UUID.randomUUID(), true, Set.of()));
    }

    @Test
    void shouldNotAllowDeactivationOfSelf() {
        UUID userId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId,true, Set.of(UserRole.OWNER));

        assertThrows(SelfDeactivationException.class,
                () -> user.deactivate(userId));
    }

    @Test
    void shouldDeactivateAndActivateUser() {
        UUID userId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId,true,Set.of(UserRole.CLIENT));

        user.deactivate(adminId);
        assertThat(user.isActive()).isFalse();

        user.activate();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void shouldChangeRoleIfValid() {
        SystemUser user = new SystemUser(UUID.randomUUID(),true, Set.of(UserRole.CLIENT));
        user.replaceRoles(Set.of(UserRole.OWNER));

        assertThat(user.getRoles()).contains(UserRole.OWNER);
    }

    @Test
    void shouldThrowExceptionWhenActivatingAlreadyActiveUser() {
        UUID userId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId,true, Set.of(UserRole.CLIENT));

        assertThrows(UserAlreadyActiveException.class, user::activate);
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingAlreadyInactiveUser() {
        UUID userId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        SystemUser user = new SystemUser(userId,true, Set.of(UserRole.CLIENT));

        user.deactivate(adminId);

        assertThrows(UserAlreadyInactiveException.class,
                () -> user.deactivate(adminId));
    }
    @Test
    void rolesShouldBeUnmodifiable() {
        SystemUser user = new SystemUser(UUID.randomUUID(),true, Set.of(UserRole.CLIENT));

        assertThrows(UnsupportedOperationException.class,
                () -> user.getRoles().add(UserRole.OWNER));
    }
}
