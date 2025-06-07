package com.reservation.owner.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {
}
