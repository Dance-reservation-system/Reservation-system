package com.reservation.owner.infrastructure.persistence;

import com.reservation.owner.domain.model.Owner;
import com.reservation.owner.domain.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class OwnerRepositoryImpl implements OwnerRepository {
    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;

    @Override
    public Owner save(Owner owner) {
        OwnerEntity entity = ownerEntityMapper.toEntity(owner);
        OwnerEntity savedEntity = ownerJpaRepository.save(entity);
        return ownerEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Owner> findById(Long id) {
        return ownerJpaRepository.findById(id)
                .map(ownerEntityMapper::toDomain);
    }

    @Override
    public List<Owner> findAll() {
        return ownerJpaRepository.findAll().stream()
                .map(ownerEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
