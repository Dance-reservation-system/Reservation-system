package com.reservation.useraccess.application;

import com.reservation.useraccess.domain.SystemUser;
import com.reservation.useraccess.domain.SystemUserRepository;
import com.reservation.useraccess.domain.UserRole;
import com.reservation.useraccess.domain.exception.UserAlreadyExistsException;
import com.reservation.useraccess.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserAccessService {
    private final SystemUserRepository systemUserRepository;

    SystemUser createUser(UUID keycloakUserId, boolean isActive, Set<UserRole> roles) {
        if(systemUserRepository.findById(keycloakUserId).isPresent()) {
            throw new UserAlreadyExistsException(keycloakUserId);
        }
        SystemUser user = new SystemUser(keycloakUserId, isActive, roles);
        return systemUserRepository.save(user);
    }

    void activateUser(UUID userId) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.activate();
        systemUserRepository.save(user);
        log.info("User {} activated", userId);
    }

    void deactivateUser(UUID userId, UUID loggedInUserId) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.deactivate(loggedInUserId);
        systemUserRepository.save(user);
        log.info("User {} deactivated by {}", userId, loggedInUserId);
    }

    void replaceUserRoles(UUID userId, Set<UserRole> roles) {
        SystemUser user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.replaceRoles(roles);
        systemUserRepository.save(user);
        log.info("Roles for user {} replaced with {}", userId, roles);
    }
}
