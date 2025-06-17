package com.reservation.useraccess.domain;

import java.util.Optional;
import java.util.UUID;

public interface SystemUserRepository {
    void save(SystemUser user);
    Optional<SystemUser> findById(UUID id);
}