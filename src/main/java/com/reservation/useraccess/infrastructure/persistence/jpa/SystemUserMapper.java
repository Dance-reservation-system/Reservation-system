package com.reservation.useraccess.infrastructure.persistence.jpa;

import com.reservation.useraccess.domain.SystemUser;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
class SystemUserMapper {

    static SystemUserEntity toEntity(SystemUser systemUser) {
        Objects.requireNonNull(systemUser, "systemUser must not be null");
        return new SystemUserEntity(
            systemUser.getKeycloakUserId(),
            systemUser.isActive(),
            systemUser.getRoles()
        );
    }

    static SystemUser toDomain(SystemUserEntity systemUserEntity) {
        Objects.requireNonNull(systemUserEntity, "systemUserEntity must not be null");
        return new SystemUser(
            systemUserEntity.getId(),
            systemUserEntity.isActive(),
            systemUserEntity.getRoles()
        );
    }
}
