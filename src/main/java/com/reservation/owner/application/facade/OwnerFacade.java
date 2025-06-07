package com.reservation.owner.application.facade;

import com.reservation.owner.application.dto.OwnerDto;
import com.reservation.owner.application.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OwnerFacade {
    private final OwnerService ownerService;

    public OwnerDto createOwner(OwnerDto ownerDto) {
        return ownerService.createOwner(ownerDto);
    }

    public OwnerDto getOwnerById(Long id) {
        return ownerService.getOwnerById(id);
    }

    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners();
    }
}
