package com.reservation.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByKeycloakUserId(String keycloakUserId);
}
