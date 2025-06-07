package com.reservation.owner.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.reservation.owner.domain.model.Owner;

@Component
class OwnerEntityMapper {

    Owner toDomain(OwnerEntity entity) {
        if (entity == null) {
            return null;
        }

        return Owner.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .keycloakUserId(entity.getKeycloakUserId())
                .build();
    }

    OwnerEntity toEntity(Owner domain) {
        if (domain == null) {
            return null;
        }

        return OwnerEntity.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .keycloakUserId(domain.getKeycloakUserId())
                .build();
    }
}
