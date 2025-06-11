package com.reservation.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reservation.owner.OwnerResponseDto.mapToDto;

@Service
@RequiredArgsConstructor
class OwnerService {
    private final OwnerRepository ownerRepository;

    @Transactional
    OwnerResponseDto createOwner(OwnerRequestDto ownerDto) {
        // TODO: Extract keycloakUserId from JWT claims when Keycloak is integrated
        String keycloakUserId = null; // Will be extracted from security context

        if (keycloakUserId != null && ownerRepository.existsByKeycloakUserId(keycloakUserId)) {
            throw OwnerException.ownerAlreadyExists(keycloakUserId);
        }

        Owner owner = new Owner(
                ownerDto.firstName(),
                ownerDto.lastName(),
                keycloakUserId
        );

        Owner savedOwner = ownerRepository.save(owner);
        return mapToDto(savedOwner);
    }

    @Transactional(readOnly = true)
    OwnerResponseDto getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .map(OwnerResponseDto::mapToDto)
                .orElseThrow(() -> OwnerException.ownerNotFound(id));
    }

    @Transactional(readOnly = true)
    List<OwnerResponseDto> getAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(OwnerResponseDto::mapToDto)
                .toList();
    }
}
