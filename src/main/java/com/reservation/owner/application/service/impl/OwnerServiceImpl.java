package com.reservation.owner.application.service.impl;

import com.reservation.common.exception.ApiException;
import com.reservation.owner.application.dto.OwnerDto;
import com.reservation.owner.application.mapper.OwnerDtoMapper;
import com.reservation.owner.application.service.OwnerService;
import com.reservation.owner.domain.model.Owner;
import com.reservation.owner.domain.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerDtoMapper ownerDtoMapper;

    @Override
    @Transactional
    public OwnerDto createOwner(OwnerDto ownerDto) {
        Owner owner = ownerDtoMapper.toDomain(ownerDto);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerDtoMapper.toDto(savedOwner);
    }

    @Override
    @Transactional(readOnly = true)
    public OwnerDto getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .map(ownerDtoMapper::toDto)
                .orElseThrow(() -> new ApiException("Owner not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(ownerDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
