package com.reservation.owner.application.mapper;

import com.reservation.owner.application.dto.OwnerDto;
import com.reservation.owner.domain.model.Owner;

public interface OwnerDtoMapper {
    Owner toDomain(OwnerDto dto);

    OwnerDto toDto(Owner domain);
}
