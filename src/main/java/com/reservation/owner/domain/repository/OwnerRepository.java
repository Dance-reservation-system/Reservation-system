package com.reservation.owner.domain.repository;

import java.util.List;
import java.util.Optional;

import com.reservation.owner.domain.model.Owner;

public interface OwnerRepository {
    Owner save(Owner owner);

    Optional<Owner> findById(Long id);

    List<Owner> findAll();
}
