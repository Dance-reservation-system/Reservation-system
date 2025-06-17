package com.reservation.useraccess.application;

import com.reservation.useraccess.domain.SystemUser;
import com.reservation.useraccess.domain.SystemUserRepository;
import com.reservation.useraccess.domain.UserRole;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
class UserAccessService {
    private final SystemUserRepository systemUserRepository;

    public UserAccessService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    void createUser(UUID keycloakUserId, boolean isActive, Set<UserRole> roles) {
        SystemUser user = new SystemUser(keycloakUserId, isActive, roles);
        systemUserRepository.save(user);
    }

    void activateUser(UUID userId) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.activate();
        systemUserRepository.save(user);
    }

    void deactivateUser(UUID userId, UUID loggedInUserId) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.deactivate(loggedInUserId);
        systemUserRepository.save(user);
    }

    void replaceUserRoles(UUID userId, Set<UserRole> roles) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.replaceRoles(roles);
        systemUserRepository.save(user);
    }
}
