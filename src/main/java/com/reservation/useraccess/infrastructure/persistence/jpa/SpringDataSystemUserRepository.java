package com.reservation.useraccess.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataSystemUserRepository extends JpaRepository<SystemUserEntity, UUID> {}
