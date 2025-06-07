package com.reservation.owner.application.mapper.impl;

import org.springframework.stereotype.Component;

import com.reservation.owner.application.dto.OwnerDto;
import com.reservation.owner.application.mapper.OwnerDtoMapper;
import com.reservation.owner.domain.model.Owner;

@Component
class OwnerDtoMapperImpl implements OwnerDtoMapper {
    @Override
    public Owner toDomain(OwnerDto dto) {
        if (dto == null) {
            return null;
        }

        return Owner.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .keycloakUserId(dto.getKeycloakUserId())
                .build();
    }

    @Override
    public OwnerDto toDto(Owner domain) {
        if (domain == null) {
            return null;
        }

        return OwnerDto.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .keycloakUserId(domain.getKeycloakUserId())
                .build();
    }
}
