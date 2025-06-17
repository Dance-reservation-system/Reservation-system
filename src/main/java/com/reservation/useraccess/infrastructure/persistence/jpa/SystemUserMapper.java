package com.reservation.useraccess.infrastructure.persistence.jpa;

import com.reservation.useraccess.domain.SystemUser;
import org.springframework.stereotype.Component;

@Component
class SystemUserMapper {

    static SystemUserEntity toEntity(SystemUser systemUser) {
        return new SystemUserEntity(
            systemUser.getKeycloakUserId(),
            systemUser.isActive(),
            systemUser.getRoles()
        );
    }

    static SystemUser toDomain(SystemUserEntity systemUserEntity) {
        return new SystemUser(
            systemUserEntity.getId(),
            systemUserEntity.isActive(),
            systemUserEntity.getRoles()
        );
    }
}
