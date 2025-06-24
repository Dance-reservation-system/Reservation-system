package com.reservation.useraccess.infrastructure.persistence.jpa;

import com.reservation.useraccess.domain.UserRole;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "system_users", schema = "public")
public class SystemUserEntity {
    @Id
    private UUID id;

    private boolean active;

    @ElementCollection
    @CollectionTable(name = "system_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRole> roles;

    public SystemUserEntity(UUID id, boolean active, Set<UserRole> roles) {
        this.id = id;
        this.active = active;
        this.roles = roles;
    }

    public SystemUserEntity() {}

    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
}