package com.reservation.useraccess.infrastructure.persistence.jpa;

import com.reservation.useraccess.domain.SystemUser;
import com.reservation.useraccess.domain.SystemUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class JpaSystemUserRepository implements SystemUserRepository {
    private final SpringDataSystemUserRepository springDataSystemUserRepository;

    public JpaSystemUserRepository(SpringDataSystemUserRepository springDataSystemUserRepository) {
        this.springDataSystemUserRepository = springDataSystemUserRepository;
    }

    @Override
    public void save(SystemUser user) {
        SystemUserEntity entity = SystemUserMapper.toEntity(user);
        springDataSystemUserRepository.save(entity);
    }

    @Override
    public Optional<SystemUser> findById(UUID id) {
        return springDataSystemUserRepository.findById(id)
                .map(SystemUserMapper::toDomain);
    }
}
