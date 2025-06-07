package com.reservation.owner.application.service;

import com.reservation.owner.application.dto.OwnerDto;

import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(OwnerDto ownerDto);

    OwnerDto getOwnerById(Long id);

    List<OwnerDto> getAllOwners();
}
